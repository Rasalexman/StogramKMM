package ru.stogram

class Greeting {


    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    suspend fun getHtml(): String {
        return Platform().getCustomers()
    }
}