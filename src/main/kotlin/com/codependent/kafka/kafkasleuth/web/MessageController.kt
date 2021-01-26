package com.codependent.kafka.kafkasleuth.web

import com.codependent.kafka.kafkasleuth.kafka.producer.KafkaProducer
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class MessageController(private val kafkaProducer: KafkaProducer) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/sendMessage")
    fun send(@RequestParam text: String): Mono<Void> {
        logger.info("send() {}", text)
        return kafkaProducer.send(text)
    }

}
