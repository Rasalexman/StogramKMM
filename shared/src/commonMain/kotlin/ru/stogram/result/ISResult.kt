package ru.stogram.result

interface ISResult {
    fun isSuccess(): Boolean
    fun isError(): Boolean
    fun isLoading(): Boolean
    fun isEmpty(): Boolean

    fun<R : Any?> flatMapOnSuccess(onSuccess: (R) -> ISResult): ISResult
    fun flatMapOnError(onError: (Error) -> ISResult): ISResult
    fun flatMapOnEmpty(onEmpty: () -> ISResult): ISResult

    fun<R : Any?> applyOnSuccess(onSuccess: (R) -> Unit): ISResult
    fun applyOnError(onError: (Error) -> Unit): ISResult
    fun applyOnEmpty(onEmpty: () -> Unit): ISResult
}

class SResultUtils {
    companion object {
        fun empty(): ISResult = Empty
        fun loading(): ISResult = Loading
        fun error(message: String, code: Int): ISResult = Error(message, code)
        fun<T: Any> success(data: T): ISResult = Success(data)
    }
}

abstract class SResult<T : Any?> : ISResult {
    open val data: T? = null
    open var isNeedHandle: Boolean = false
    open var isHandled: Boolean = false

    override fun isSuccess(): Boolean {
        return this is Success
    }

    override fun isError(): Boolean {
        return this is Error
    }

    override fun isLoading(): Boolean {
        return this is Loading
    }

    override fun isEmpty(): Boolean {
        return this is Empty
    }

    @Suppress("UNCHECKED_CAST")
    override fun <R> flatMapOnSuccess(onSuccess: (R) -> ISResult): ISResult {
        if(this is Success) {
            (data as? R)?.let {
                return onSuccess.invoke(it)
            }
        }
        return this
    }

    override fun flatMapOnEmpty(onEmpty: () -> ISResult): ISResult {
        if(this is Empty) {
            return onEmpty.invoke()
        }
        return this
    }

    override fun flatMapOnError(onError: (Error) -> ISResult): ISResult {
        if(this is Error) {
            return onError.invoke(this)
        }
        return this
    }

    override fun <R> applyOnSuccess(onSuccess: (R) -> Unit): ISResult {
        if(this is Success) {
            (data as? R)?.let(onSuccess)
        }
        return this
    }

    override fun applyOnEmpty(onEmpty: () -> Unit): ISResult {
        if(this is Empty) {
            onEmpty.invoke()
        }
        return  this
    }

    override fun applyOnError(onError: (Error) -> Unit): ISResult {
        if(this is Error) {
            onError.invoke(this)
        }
        return this
    }
}

abstract class AnyResult : SResult<Any>()

abstract class AbstractFailure(
    override val data: Any? = null
) : AnyResult() {
    open val message: String? = null
    open val code: Int = 0
}

class Success<T>(
    override val data: T
) : SResult<T>()

class Error(
    override val message: String,
    override val code: Int = 0
) : AbstractFailure()

object Loading : AnyResult()
object Empty : AnyResult()