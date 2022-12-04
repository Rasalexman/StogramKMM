package ru.stogram.android.common

import java.util.*

fun String?.orZero(): String {
    return this ?: "0"
}

fun String?.orRandom(): String {
    return this ?: UUID.randomUUID().toString()
}