package ru.stogram

import io.ktor.client.*
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val client: HttpClient = HttpClient()
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual suspend fun getCustomers(): String {
        return ""
    }
}