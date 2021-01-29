package com.codependent.kafka.kafkasleuth.kafka.producer;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.OffsetDateTime;

@Component
public class KafkaProducer {

    private final Sinks.Many<Message<String>> textSink;

    public KafkaProducer(Sinks.Many<Message<String>> textSink) {
        this.textSink = textSink;
    }

    public Mono<Void> send(String text) {
        long dateTime = OffsetDateTime.now().toInstant().toEpochMilli();

        Message<String> message = MessageBuilder.withPayload(text)
                .setHeader(KafkaHeaders.MESSAGE_KEY, text)
                .setHeader(KafkaHeaders.TIMESTAMP, dateTime)
                .build();

        textSink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
        return Mono.empty();
    }
}
