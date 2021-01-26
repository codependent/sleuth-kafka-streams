package com.codependent.kafka.kafkasleuth.kafka.stream

import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.processor.Processor
import org.apache.kafka.streams.processor.ProcessorContext
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class TextKStreamConfiguration {

    @Bean
    fun process() = Consumer<KStream<String, String>> { textStream ->
        textStream.process({

            object : Processor<String, String> {

                private val logger = LoggerFactory.getLogger(javaClass)
                private lateinit var context: ProcessorContext

                override fun init(context: ProcessorContext) {
                    this.context = context
                }

                override fun process(key: String, value: String) {
                    logger.info("Headers(b3) => {}", String(context.headers().lastHeader("b3").value()))
                    logger.info("Key {} - Value {}", key, value)
                }

                override fun close() {}
            }
        })
    }

}
