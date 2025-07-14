package com.example.proyecto2025_BE.service;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;


@Component
@Slf4j
public class IngestionService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;

    @Value("classpath:/docs/toc.pdf")
    private Resource elPDF;

    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }


    @Override
    public void run(String... args) throws Exception {
        var pdfReader = new PagePdfDocumentReader(elPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(pdfReader.get()));
        log.info("La db fue cargada con dataa!");
    }
}
