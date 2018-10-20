package com.example.multicastingtest

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class SomethingElse(private val webClient: WebClient) {


}