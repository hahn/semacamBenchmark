package id.husna.benchglide.network

import android.app.Service
import id.husna.benchglide.BuildConfig
import id.husna.benchglide.di.Injector
import id.husna.benchglide.di.Module
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
object NetworkModule {
    class Factory : Module() {
//        private val cacheInteractor by lazy(LazyThreadSafetyMode.NONE) {
//            Injector.get<CacheProvider>()
//        }

        private val okHttp by lazy(LazyThreadSafetyMode.NONE) {
            OkHttpClient.Builder()
//                .addNetworkInterceptor(cacheInteractor.getInterceptor())
//                .cache(cacheInteractor.cache)
                .build()
        }

        private val retrofit by lazy(LazyThreadSafetyMode.NONE) {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .baseUrl(BuildConfig.BASE_URL)
                .build()
        }

        override fun provide() {
            provide<ServiceApi>(Factory().create())
        }

        private fun create(): ServiceApi {
            return retrofit.create(ServiceApi::class.java)
        }
    }
}