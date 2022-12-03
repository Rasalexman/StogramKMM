package ru.stogram.android.constants

object ScreenNames {
    const val Login = "login"
    const val Register = "register"

    const val Main = "main"
    const val HOME = "home"
    const val SEARCH = "search"
    const val CREATE = "create"
    const val REACTIONS = "reactions"
    const val PROFILE = "profile"

    const val POST_DETAILS = "post/{${ArgsNames.POST_ID}}"
    const val POST_COMMENTS = "post/comments/{${ArgsNames.POST_ID}}"
    const val USER_PROFILE = "user/{${ArgsNames.USER_ID}}"
}