package ru.stogram.utils

import kotlin.random.Random

private val namesCombs = listOf(
    "Ivan", "Disco", "Chillout",
    "Vocal", "Deep", "Mix", "Soundeo",
    "House", "Fabulous", "SoundCloud",
    "However", "Catalina", "Xcode",
    "Product", "Overview", "Public",
    "Session", "Disconnect", "Respond",
    "Request", "Dapp", "Kotlin",
    "Parabéns", "Really", "Belissimo", "Beautiful"
)

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

val randomCount: String
    get() = Random.nextInt(10, 99999).toString()

val randomBool: Boolean
    get() = Random.nextInt(0, 2)%2==0

fun getRandomName(): String {
    val randomCount: Int = Random.nextInt(0, namesCombs.size-1)
    val firstName = namesCombs[randomCount]
    val distinctNames = namesCombs.filter { it != firstName }
    val randomCount2: Int = Random.nextInt(0, distinctNames.size-1)
    val secondName = distinctNames[randomCount2]
    return "$firstName $secondName"
}