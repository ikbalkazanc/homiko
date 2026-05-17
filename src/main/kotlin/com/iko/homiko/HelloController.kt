package com.iko.homiko

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HelloController {

	@GetMapping("/hello")
	fun hello(): HelloResponse = HelloResponse(
		message = "Merhaba, Homiko çalışıyor!",
		app = "homiko",
	)

	@GetMapping("/info")
	fun info(): InfoResponse = InfoResponse(
		name = "Homiko",
		description = "Kotlin + Spring Boot test uygulaması",
		version = "0.0.1-SNAPSHOT",
	)
}

data class HelloResponse(
	val message: String,
	val app: String,
)

data class InfoResponse(
	val name: String,
	val description: String,
	val version: String,
)
