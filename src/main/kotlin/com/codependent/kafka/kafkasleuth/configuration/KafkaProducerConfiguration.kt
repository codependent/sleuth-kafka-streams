package com.codependent.kafka.kafkasleuth.configuration

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.BeanFactory
import org.springframework.cloud.sleuth.instrument.messaging.MessagingSleuthOperators
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.util.function.Supplier

/**
 * @author José A. Íñigo
 */
@Configuration
class KafkaProducerConfiguration(private val beanFactory: BeanFactory) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun textSink(): Sinks.Many<Message<String>> = Sinks.many().unicast().onBackpressureBuffer()

    @Bean
    fun text(): Supplier<Flux<Message<String>>> = Supplier {
        textSink().asFlux().map {
            val msg = MessagingSleuthOperators.handleOutputMessage(beanFactory, MessagingSleuthOperators.forInputMessage(beanFactory, it))
            logger.debug("Produced message {}", msg)
            msg
        }
    }

}
