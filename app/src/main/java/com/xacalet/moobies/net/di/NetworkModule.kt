package com.xacalet.moobies.net.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getOkHttpClient(@Named("api_key") apiKey: String): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()

                val url = request.url().newBuilder()
                    .addQueryParameter("api_key", apiKey)
                    .build()

                val newRequest = request.newBuilder()
                    .url(url)
                    .build()

                chain.proceed(newRequest)
            }
            .build()

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/")
            .client(okHttpClient)
            .build()

    // TODO: Consider moving this API key to the BuildInfo
    @Provides
    @Singleton
    @Named("api_key")
    fun getApiKey(): String = "35ec220e24d0c24bfa50fedacbbabdaf"
}
