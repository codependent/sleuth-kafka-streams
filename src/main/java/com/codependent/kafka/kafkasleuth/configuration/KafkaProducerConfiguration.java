package com.codependent.kafka.kafkasleuth.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.messaging.MessagingSleuthOperators;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class KafkaProducerConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BeanFactory beanFactory;

    public KafkaProducerConfiguration(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    @Bean
    public Sinks.Many<Message<String>> textSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }


    @Bean
    public Supplier<Flux<Message<String>>> text() {
        return () -> textSink().asFlux().map(it -> {
            Message<String> msg = MessagingSleuthOperators.handleOutputMessage(beanFactory, MessagingSleuthOperators.forInputMessage(beanFactory, it));
            logger.debug("Produced message {}", msg);
            return msg;
        });
    }
}
