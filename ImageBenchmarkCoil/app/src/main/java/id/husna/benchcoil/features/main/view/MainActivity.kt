package id.husna.benchcoil.features.main.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import id.husna.benchcoil.R
import id.husna.benchcoil.di.Injector
import id.husna.benchcoil.features.main.data.MainRepositoryImpl
import id.husna.benchcoil.features.main.domain.MainUseCaseImpl
import id.husna.benchcoil.helpers.ViewState
import id.husna.benchcoil.helpers.showToast
import id.husna.benchcoil.network.response.UnsplashResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var adapter: UnsplashAdapter
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { Injector.get<MainViewModel>()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        deleteCache()
        initGraph()
        initView()
        initSwipeRefresh()
        initObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.remove(viewModel)
    }

    private fun deleteCache() {
        val deleted = this.cacheDir.deleteRecursively()
        Log.d("TAG", "time: $deleted")
    }

    private fun initGraph() {
        MainRepositoryImpl(Injector.get())
            .let(::MainUseCaseImpl)
            .let { MainViewModel(Dispatchers.IO, it) }
            .let(Injector::add)
    }

    private fun initSwipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            deleteCache()
            viewModel.refresh()
        }
    }

    private fun initView() {
        adapter = UnsplashAdapter(this)
        val layoutManager = GridLayoutManager(this, 2)
        rv_image.layoutManager = layoutManager
        rv_image.adapter = adapter
        rv_image.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }

    private fun initObserver() {
        viewModel.viewState.observe(this, Observer {
            adapter.setViewState(it)
            handleViewState(it)
        })
        viewModel.images.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun handleViewState(viewState: ViewState) {
        when(viewState) {
            is ViewState.ERROR -> {
                if (!viewState.msg.isNullOrBlank()) this.showToast(viewState.msg)
                hideLoading()
            }
            is ViewState.SUCCESS -> {
                hideLoading()
            }
            is ViewState.LOADING -> {
                if (adapter.itemCount == 0) swipe_refresh.isRefreshing = true
                else {
                    swipe_refresh.isEnabled = false
                }
            }
        }
    }

    private fun hideLoading() {
        swipe_refresh.run {
            isRefreshing = false
            isEnabled = true
        }
        adapter.isRefresh = false
    }

    override fun onItemClickListener(item: UnsplashResponse) {
        showToast(item.altDescription.orEmpty())
    }

}
