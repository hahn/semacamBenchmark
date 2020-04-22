package id.husna.imagebenchmarkbase

import android.app.Application
import android.content.Context
import id.husna.imagebenchmarkbase.di.Injector
import id.husna.imagebenchmarkbase.network.CacheProvider
import id.husna.imagebenchmarkbase.network.CacheProviderImpl
import id.husna.imagebenchmarkbase.network.NetworkModule

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