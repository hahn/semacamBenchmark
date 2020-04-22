package id.husna.imagebenchmarkbase.features.main.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.husna.imagebenchmarkbase.features.main.domain.MainUseCase
import id.husna.imagebenchmarkbase.network.response.UnsplashResponse
import kotlinx.coroutines.CoroutineScope

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
class ImageDataSourceFactory(
    private val useCase: MainUseCase,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, UnsplashResponse>() {

    val source = MutableLiveData<ImageDataSource>()
    override fun create(): DataSource<Int, UnsplashResponse> {
        val _source = ImageDataSource(useCase, scope)
        source.postValue(_source)
        return _source
    }
}