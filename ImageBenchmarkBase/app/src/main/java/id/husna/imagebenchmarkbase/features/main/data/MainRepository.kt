package id.husna.imagebenchmarkbase.features.main.data

import android.util.Log
import id.husna.imagebenchmarkbase.BuildConfig
import id.husna.imagebenchmarkbase.helpers.Either
import id.husna.imagebenchmarkbase.helpers.failure
import id.husna.imagebenchmarkbase.helpers.value
import id.husna.imagebenchmarkbase.network.ServiceApi
import id.husna.imagebenchmarkbase.network.response.UnsplashResponse

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
interface MainRepository {
    suspend fun fetchPhotos(page: Int): Either<Throwable, List<UnsplashResponse>>
}

class MainRepositoryImpl(private val serviceApi: ServiceApi) : MainRepository {
    override suspend fun fetchPhotos(page: Int): Either<Throwable, List<UnsplashResponse>> {
        val clientId = BuildConfig.ACCESS_KEY
        Log.d("TAG", "page: $page")
        kotlin.runCatching {
            return value(serviceApi.getPhotos(page, clientId))
        }.getOrElse { error ->
            error.printStackTrace()
            return failure(error)
        }
    }
}