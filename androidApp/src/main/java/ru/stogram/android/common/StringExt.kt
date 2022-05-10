package ru.stogram.android.common

fun String?.orZero(): String {
    return this ?: "0"
}