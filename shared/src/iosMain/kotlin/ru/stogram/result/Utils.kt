package ru.stogram.result

import com.rasalexman.sresult.data.dto.ISResult
import com.rasalexman.sresult.data.dto.SResult

object Utils {
    fun empty(): ISResult = SResult.Empty
    fun loading(): ISResult = SResult.Loading()
    fun error(message: String, code: Int): ISResult = SResult.AbstractFailure.Error(message, code)
    fun<T: Any> success(data: T): ISResult = SResult.Success(data)
}