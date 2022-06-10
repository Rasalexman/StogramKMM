package ru.stogram.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*


// Credit - https://github.com/JetBrains/kotlinconf-app/blob/master/common/src/mobileMain/kotlin/org/jetbrains/kotlinconf/FlowUtils.kt
// Wrapper to consume Flow based API from Obj-C/Swift
// Alternatively we can use the 'Kotlinx_coroutines_coreFlowCollector' protocol from Swift as demonstrated in https://stackoverflow.com/a/66030092
// however the below wrapper gives us more control and hides the complexity in the shared Kotlin code.
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
open class CFlow<T>(protected val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = SupervisorJob()

        onEach {
            block(it)
            // [SwiftUI] Publishing changes from background threads is not allowed;
            // make sure to publish values from the main thread
        }.launchIn(CoroutineScope(Dispatchers.Main + job))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }

    fun <R> flatMapCFlow(flatBlock: (T) -> CFlow<R>): CFlow<R> {
        return this.flatMapLatest {
            flatBlock(it)
        }.wrap()
    }

    fun <R> mapCFlow(mapBlock: (T) -> R): CFlow<R> {
        return this.map {
            mapBlock(it)
        }.wrap()
    }

    fun <P, R> combineSingle(input: CFlow<P>, block: (T, P) -> R): CFlow<R> {
        return combine(this, input) { first, second ->
            block(first, second)
        } .wrap()
    }

    open fun emit(data: T): Boolean {
        return false
    }
}

class CStateFlow<T>(value: T) : CFlow<T>(MutableStateFlow<T>(value)) {

    override fun emit(data: T): Boolean {
        return (this.origin as? MutableStateFlow<T>)?.tryEmit(data) ?: false
    }

    companion object {
        fun convertString(data: String): CStateFlow<String> {
            return CStateFlow(data)
        }
    }

}

// Helper extension
internal fun <T> Flow<T>.wrap(): CFlow<T> = CFlow(this)

// Remove when Kotlin's Closeable is supported in K/N https://youtrack.jetbrains.com/issue/KT-31066
// Alternatively use Ktor Closeable which is K/N ready.
interface Closeable {
    fun close()
}