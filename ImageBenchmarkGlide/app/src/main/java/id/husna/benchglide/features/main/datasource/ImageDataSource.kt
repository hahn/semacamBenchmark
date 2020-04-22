package id.husna.benchglide.features.main.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.husna.benchglide.features.main.domain.MainUseCase
import id.husna.benchglide.helpers.Either
import id.husna.benchglide.helpers.ViewState
import id.husna.benchglide.network.response.UnsplashResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
class ImageDataSource(
    private val useCase: MainUseCase,
    private val scope: CoroutineScope
): PageKeyedDataSource<Int, UnsplashResponse>() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState
    private val maxPage = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, UnsplashResponse>
    ) {
        scope.launch {
            _viewState.postValue(ViewState.LOADING)
            when(val result = useCase.fetchPhotos()) {
                is Either.Success -> {
                    _viewState.postValue(ViewState.SUCCESS)
                    val nextPage = null//if (result.value.isEmpty()) null else 2
                    callback.onResult(result.value, null, nextPage)
                }
                is Either.Failure -> {
                    _viewState.postValue(ViewState.ERROR("error"))
                    result.error.printStackTrace()
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UnsplashResponse>) {
        scope.launch {
            _viewState.postValue(ViewState.LOADING)
            when(val result = useCase.fetchPhotos(params.key)) {
                is Either.Success -> {
                    _viewState.postValue(ViewState.SUCCESS)
                    val nextPage = if (result.value.isEmpty() || params.key + 1 > maxPage) null else params.key + 1
                    callback.onResult(result.value, nextPage)
                }
                is Either.Failure -> _viewState.postValue(ViewState.ERROR("error"))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UnsplashResponse>) {}
}