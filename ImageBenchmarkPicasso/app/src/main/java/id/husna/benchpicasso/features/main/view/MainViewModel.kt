package id.husna.benchpicasso.features.main.view

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.husna.benchpicasso.features.main.datasource.ImageDataSourceFactory
import id.husna.benchpicasso.features.main.domain.MainUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
class MainViewModel(
    private val contextProvider: CoroutineDispatcher,
    private val mainUseCase: MainUseCase) : ViewModel() {

    private val coroutineScope = CoroutineScope(viewModelScope.coroutineContext + contextProvider)
    private val imageDataSource = ImageDataSourceFactory(mainUseCase, coroutineScope)

    private val pageListConfig = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setEnablePlaceholders(false).build()

    val images = LivePagedListBuilder(imageDataSource, pageListConfig).build()
    val viewState = Transformations.switchMap(imageDataSource.source){it.viewState}

    fun refresh() {
        imageDataSource.source.value?.invalidate()
    }

    companion object {
        private const val PAGE_SIZE = 10
    }

}