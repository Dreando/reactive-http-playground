package com.example.multicastingtest.channel

import java.util.*

data class AccessToken(val token: String) {

    fun matches(otherToken: AccessToken) = this.token == otherToken.token

    companion object {
        fun random() = AccessToken(UUID.randomUUID().toString())
    }
}