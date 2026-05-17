package com.iko.homiko

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HomikoApplication

fun main(args: Array<String>) {
	runApplication<HomikoApplication>(*args)
}
