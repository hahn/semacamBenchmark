package id.husna.benchpicasso.features.main.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.husna.benchpicasso.R
import id.husna.benchpicasso.di.Injector
import id.husna.benchpicasso.features.main.data.MainRepositoryImpl
import id.husna.benchpicasso.features.main.domain.MainUseCaseImpl
import id.husna.benchpicasso.helpers.Either
import id.husna.benchpicasso.helpers.ViewState
import id.husna.benchpicasso.helpers.showToast
import id.husna.benchpicasso.network.response.UnsplashResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

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
