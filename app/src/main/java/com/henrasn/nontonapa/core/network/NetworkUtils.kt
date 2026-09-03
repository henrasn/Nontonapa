package com.henrasn.nontonapa.core.network

import com.henrasn.nontonapa.R
import com.henrasn.nontonapa.core.error.AppException
import com.henrasn.nontonapa.core.error.ErrorUiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    crossinline apiCall: suspend () -> T
): Result<T> {
    return try {
        withContext(dispatcher) {
            Result.success(apiCall())
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: ApiException) {
        Result.failure(
            AppException(
                ErrorUiText.DynamicString(
                    e.httpMessage ?: e.httpCode.toString()
                ), e
            )
        )
    } catch (e: SerializationException) {
        Result.failure(AppException(ErrorUiText.StringResource(R.string.err_resp_parsing), e))
    } catch (e: IOException) {
        Result.failure(AppException(ErrorUiText.StringResource(R.string.err_no_connect), e))
    } catch (e: Exception) {
        Result.failure(AppException(ErrorUiText.StringResource(R.string.err_unknown), e))
    }
}