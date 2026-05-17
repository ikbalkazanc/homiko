package com.iko.homiko

import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api")
class HelloController(
    private val environment: Environment,
) {

    private val log = LoggerFactory.getLogger(HelloController::class.java)

    @GetMapping("/hello")
    fun hello(): HelloResponse = HelloResponse(
        message = "Merhaba, Homiko çalışıyor!",
        app = "homiko",
    )

    @GetMapping("/info")
    fun info(): InfoResponse = InfoResponse(
        name = "Homiko",
        description = "Kotlin + Spring Boot test uygulaması",
        version = appVersion(),
    )

    @GetMapping("/version")
    fun version(): VersionResponse {
        val appVersion = appVersion()
        val profile = environment.activeProfiles.firstOrNull() ?: "default"

        log.info(
            "loki_probe version_requested app=homiko version={} profile={} at={}",
            appVersion,
            profile,
            Instant.now(),
        )

        return VersionResponse(
            app = "homiko",
            version = appVersion,
            profile = profile,
        )
    }

    private fun appVersion(): String =
        HelloController::class.java.`package`?.implementationVersion ?: "0.0.1-SNAPSHOT"
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

data class VersionResponse(
    val app: String,
    val version: String,
    val profile: String,
)