package com.codependent.kafka.kafkasleuth.web;

import com.codependent.kafka.kafkasleuth.kafka.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/sendMessage")
    public Mono<Void> send(@RequestParam String text){
        logger.info("send() {}", text);
        return kafkaProducer.send(text);
    }

}
