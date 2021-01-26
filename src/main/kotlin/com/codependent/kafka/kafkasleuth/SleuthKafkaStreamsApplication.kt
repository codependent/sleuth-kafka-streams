package com.codependent.kafka.kafkasleuth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaSleuthApplication

fun main(args: Array<String>) {
	runApplication<KafkaSleuthApplication>(*args)
}
