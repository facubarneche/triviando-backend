package com.example.proyecto2025_BE.service;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import dev.langchain4j.http.client.jdk.JdkHttpClient;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilder;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
//@RequiredArgsConstructor
public class ApiClient implements ChatLanguageModel {
	
	private static final String TEMPLATE_PROMPT = "Build me 3 short questions about ";
	
    public Flux<Object> post(String topic) {
    	 return Flux.create(sink -> {
    		 String prompt = TEMPLATE_PROMPT + topic;
	    	 TokenStream tokenStream = chatWithModel(prompt);
	    	 StringBuilder fullResponse = new StringBuilder("");
    	    	
             tokenStream.onPartialResponse(partialResponse -> {
                         sink.next(partialResponse); 
                         fullResponse.append(partialResponse);
                     })
                     .onCompleteResponse(response -> {
                         sink.complete();
                         log.info("Respuesta completa (TokenStream): {}", fullResponse.toString());
                     })
                     .onError(sink::error)
                     .start();
         });
    }
    
    private TokenStream chatWithModel(String message) {
    	ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
    	ModelCommunication modelCommunication = AiServices.builder(ModelCommunication.class)
                .streamingChatLanguageModel(model())
                .chatMemory(chatMemory)
                .build();
    	
        return modelCommunication.chatWithModel(message);
    }
    

	//TODO: pasar credenciales a el application.yml
    private StreamingChatLanguageModel model() {
		HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
				.authenticator(new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("gemma3", ("LosPibitosDeLaUnsamMandan.".toCharArray()));
					}
				});
    	JdkHttpClientBuilder jdkHttpClientBuilder = JdkHttpClient.builder()
    	        .httpClientBuilder(httpClientBuilder)
				.connectTimeout(Duration.ofMinutes(2))
				.readTimeout(Duration.ofMinutes(2));

		//TODO: posibilidad de agregar la authenticacion por medio del metodo customHeader dado por el builder sin necesidad de crear un JdkHttpClientBuilder
    	return OllamaStreamingChatModel.builder()
    	        .baseUrl("https://busy-smooth-sunbeam.ngrok-free.app")
    	        .modelName("gemma3")
				.logRequests(Boolean.TRUE)
				.logResponses(Boolean.TRUE)
    	        .httpClientBuilder(jdkHttpClientBuilder)
//    	        .timeout(Duration.ofMinutes(2))
    	        .build();
    }
}
