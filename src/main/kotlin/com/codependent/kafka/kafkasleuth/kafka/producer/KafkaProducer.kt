package com.codependent.kafka.kafkasleuth.kafka.producer

import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.time.OffsetDateTime

@Component
class KafkaProducer(private val textSink: Sinks.Many<Message<String>>) {

    fun send(text: String): Mono<Void> {
        val dateTime = OffsetDateTime.now().toInstant().toEpochMilli()

        val message = MessageBuilder.withPayload(text)
            .setHeader(KafkaHeaders.MESSAGE_KEY, text)
            .setHeader(KafkaHeaders.TIMESTAMP, dateTime)
            .build()

        textSink.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST)
        return Mono.empty()
    }
}
