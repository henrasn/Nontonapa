package com.henrasn.nontonapa.core.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwODZmMjg4ODhhZmM3ZmNmMGJhODVlZTdlZDBmY2QwZCIsIm5iZiI6MTUxNzA0ODkxMi4xNzUsInN1YiI6IjVhNmM1NDUwYzNhMzY4NTU0NDAwODViZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bshy_F8uNl0gFtxTVgd2iIeHNAu0WOtqIrOfTeOpP6g"
            )
            .build()
        return chain.proceed(request)
    }
}