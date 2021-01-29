package com.codependent.kafka.kafkasleuth.kafka.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class TextKStreamConfiguration {

    @Bean
    public Consumer<KStream<String, String>> process() {
        return textStream -> textStream.process(() -> new Processor<>() {

            private final Logger logger = LoggerFactory.getLogger(getClass());
            private ProcessorContext context;

            @Override
            public void init(ProcessorContext context) {
                this.context = context;
            }

            @Override
            public void process(String key, String value) {
                logger.info("Headers(b3) => {}", new String(context.headers().lastHeader("b3").value()));
                logger.info("Key {} - Value {}", key, value);
            }

            @Override
            public void close() {

            }

        });

    }

}
