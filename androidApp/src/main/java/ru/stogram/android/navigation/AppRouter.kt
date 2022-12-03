package ru.stogram.android.navigation

class AppRouter : MainHostRouter(), IAppRouter

interface IAppRouter : IHostRouter, IMainRouter

interface IBackToHostRouter {
    fun popBackToHost()
}