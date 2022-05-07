package ru.stogram

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.net.InetSocketAddress
import java.net.Proxy

actual class Platform actual constructor() {

    actual val client: HttpClient by lazy {
        HttpClient(Android) {
            engine {
                // this: AndroidEngineConfig
                sslManager
                proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("127.0.0.1", 8080))
            }
        }
    }

    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"

    actual suspend fun getCustomers(): String {
        val respond = client.get("/customers")
        return respond.bodyAsText()
    }
}