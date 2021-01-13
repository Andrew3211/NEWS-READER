package com.vendur.newsreader.api

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val BASE_URL : String = "https://newsapi.org/v2/"
lateinit var retrofit : Retrofit

fun getApiClient() : Retrofit {
    retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getUnsafeOkHttpClient().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}

fun getUnsafeOkHttpClient() : OkHttpClient.Builder {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager{
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())
    val sslSocketFactory = sslContext.socketFactory
    val builder = OkHttpClient.Builder()
    builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    builder.hostnameVerifier{_, _ -> true }

    return builder
}