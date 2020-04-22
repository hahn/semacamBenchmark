package id.husna.benchpicasso

import android.app.Application
import android.content.Context
import id.husna.benchpicasso.di.Injector
import id.husna.benchpicasso.network.CacheProvider
import id.husna.benchpicasso.network.CacheProviderImpl
import id.husna.benchpicasso.network.NetworkModule

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
class ImageBenchmarkApp: Application() {

    private val context: Context by lazy(LazyThreadSafetyMode.NONE) { this }

    override fun onCreate() {
        super.onCreate()
        initGraph()
    }

    private fun initGraph() {
        Injector.provide<CacheProvider>(CacheProviderImpl(context.cacheDir))
        Injector.provide<NetworkModule.Factory>()
    }
}