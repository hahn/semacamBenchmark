package id.husna.benchcoil.network

import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
interface CacheProvider {
    fun getInterceptor(): Interceptor
    val cache: Cache
}

class CacheProviderImpl(private val cacheDir: File): CacheProvider, Interceptor {
    private val cacheControl by lazy {
        CacheControl.Builder()
            .maxStale(1, TimeUnit.HOURS)
            .maxAge(1, TimeUnit.HOURS)
            .build()
    }

    override val cache by lazy {
        Cache(cacheDir, 10 * 1024 * 1024)
    }

    override fun getInterceptor(): Interceptor {
        return this
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        return response.newBuilder()
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build();
    }

}