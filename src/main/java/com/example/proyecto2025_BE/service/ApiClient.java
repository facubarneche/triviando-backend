package com.example.proyecto2025_BE.service;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import dev.langchain4j.service.SystemMessage;
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

	private final StreamingChatLanguageModel languageModel;
	private final ModelCommunication assistant;

	public ApiClient() {
		this.languageModel = connectModel("https://busy-smooth-sunbeam.ngrok-free.app", "gemma3");
		// Memorize for 10 messages continuously
		ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
		this.assistant = AiServices.builder(ModelCommunication.class)
				// Alternative of .chatLanguageModel() which support streaming response
				.streamingChatLanguageModel(this.languageModel)
				.chatMemory(chatMemory)
				.build();
	}


//	@SystemMessage("""
//            You are a questions answers generator.
//            You are directly, polite and concise.
//            And when come to something about the prevention of office syndrome, you are the best adviser.
//            You response will always as bullet points and keep it short in this format
//                * question ; option 1 - (explication); option 2 - (explication); option 3 - (explication)
//    	""")
	public TokenStream chatWithModel(String message) {
		return assistant.chatWithModel(message);
	}

	public CompletableFuture<Void> post(String topic) {

	 	String prompt = TEMPLATE_PROMPT + topic;
		TokenStream tokenStream = chatWithModel(prompt);

		CompletableFuture<Void> future = new CompletableFuture<>();

		tokenStream.onPartialResponse(System.out::print)
				.onCompleteResponse(lal -> {
					System.out.println();
					future.complete(null);
				})
				.onError(Throwable::printStackTrace)
				.start();
		return future;
	}

//    public Flux<Object> post(String topic) {
//    	 return Flux.create(sink -> {
//    		 String prompt = TEMPLATE_PROMPT + topic;
//	    	 TokenStream tokenStream = chatWithModel(prompt);
//	    	 StringBuilder fullResponse = new StringBuilder("");
//
//             tokenStream.onPartialResponse(partialResponse -> {
//                         sink.next(partialResponse);
//                         fullResponse.append(partialResponse);
//                     })
//                     .onCompleteResponse(response -> {
//                         sink.complete();
//                         log.info("Respuesta completa (TokenStream): {}", fullResponse.toString());
//                     })
//                     .onError(sink::error)
//                     .start();
//         });
//    }

	//TODO: pasar credenciales a el application.yml
    private StreamingChatLanguageModel connectModel(String modelUrl, String modelName) {
//		HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
//				.authenticator(new Authenticator() {
//					@Override
//					protected PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication("gemma3", ("LosPibitosDeLaUnsamMandan.".toCharArray()));
//					}
//				});
//    	JdkHttpClientBuilder jdkHttpClientBuilder = JdkHttpClient.builder()
//    	        .httpClientBuilder(httpClientBuilder)
//				.connectTimeout(Duration.ofMinutes(2))
//				.readTimeout(Duration.ofMinutes(2));

		return OllamaStreamingChatModel.builder()
    	        .baseUrl(modelUrl)
    	        .modelName(modelName)
				.customHeaders(Map.of("Authorization", "Basic Z2VtbWEzOkxvc1BpYml0b3NEZUxhVW5zYW1NYW5kYW4u"))
				.logRequests(Boolean.TRUE)
				.logResponses(Boolean.TRUE)
    	        .timeout(Duration.ofMinutes(1))
    	        .build();
    }
}
