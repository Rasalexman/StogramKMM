package ru.stogram

import io.ktor.client.*

expect class Platform() {
    val platform: String
    val client: HttpClient

    suspend fun getCustomers(): String
}