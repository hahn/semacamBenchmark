package id.husna.benchpicasso.features.main.domain

import id.husna.benchpicasso.features.main.data.MainRepository
import id.husna.benchpicasso.helpers.Either
import id.husna.benchpicasso.network.response.UnsplashResponse

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
interface MainUseCase {
    suspend fun fetchPhotos(page: Int = 1): Either<Throwable, List<UnsplashResponse>>
}

class MainUseCaseImpl(private val repository: MainRepository) : MainUseCase {
    override suspend fun fetchPhotos(page: Int): Either<Throwable, List<UnsplashResponse>> {
        return repository.fetchPhotos(page)
    }
}