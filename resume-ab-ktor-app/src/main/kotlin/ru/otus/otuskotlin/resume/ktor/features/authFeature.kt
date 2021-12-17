package ru.otus.otuskotlin.resume.ktor.features

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import ru.otus.otuskotlin.resume.ktor.configs.AppKtorConfig
import ru.otus.otuskotlin.resume.ktor.configs.KtorAuthConfig.Companion.GROUPS_CLAIM

fun Application.authFeature(config: AppKtorConfig) {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = config.auth.realm
            verifier(
                JWT.require(Algorithm.HMAC256(config.auth.secret))
                    .withAudience(config.auth.audience)
                    .withIssuer(config.auth.issuer)
                    .build()
            )
            validate { jwtCredential: JWTCredential ->
                when {
                    !jwtCredential.payload.audience.contains(config.auth.audience) -> {
                        log.error("Unsupported audience in JWT token ${jwtCredential.payload.audience}. Must be ${config.auth.audience}")
                        null
                    }
                    jwtCredential.payload.issuer != config.auth.issuer -> {
                        log.error("Wrong issuer in JWT token ${jwtCredential.payload.issuer}. Must be ${config.auth.issuer}")
                        null
                    }
//                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
//                        log.error("Groups claim must not be empty in JWT token: ${jwtCredential.payload.getClaim(GROUPS_CLAIM)}")
//                        null
//                    }
                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }
}