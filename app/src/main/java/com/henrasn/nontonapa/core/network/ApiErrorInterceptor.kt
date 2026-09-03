package com.henrasn.nontonapa.core.network

import com.henrasn.nontonapa.data.model.dto.genre.ErrorResponse
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response

class ApiErrorInterceptor : Interceptor {
    private val json = Json { ignoreUnknownKeys = true }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            val errorResponse = response.body?.string()?.let { body ->
                try {
                    json.decodeFromString<ErrorResponse>(body)
                } catch (e: Exception) {
                    null
                }
            }

            response.close()
            throw ApiException(
                response.code,
                errorResponse?.statusCode ?: -1,
                errorResponse?.statusMessage
            )
        }

        return response
    }
}

