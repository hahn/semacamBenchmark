package id.husna.benchcoil.features.main.view

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.annotation.ExperimentalCoil
import id.husna.benchcoil.R
import id.husna.benchcoil.helpers.ViewState
import id.husna.benchcoil.helpers.inflate
import id.husna.benchcoil.helpers.setImageFromUrl
import id.husna.benchcoil.network.response.UnsplashResponse
import kotlinx.android.synthetic.main.item_image.view.*
import kotlin.time.ExperimentalTime

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
class UnsplashAdapter(private val listener: OnClickListener) :
    PagedListAdapter<UnsplashResponse, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<UnsplashResponse>() {
    override fun areItemsTheSame(oldItem: UnsplashResponse, newItem: UnsplashResponse): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UnsplashResponse, newItem: UnsplashResponse): Boolean = oldItem.id == newItem.id
})  {
    var isRefresh: Boolean = false
    private var viewState: ViewState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.item_image -> DataViewHolder(parent.inflate(viewType))
            R.layout.item_loading -> LoadingViewHolder(parent.inflate(viewType))
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    @ExperimentalCoil
    @ExperimentalTime
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == R.layout.item_image) {
            (holder as DataViewHolder).bind(getItem(position), listener)
        }
    }

    private val hasExtraRow: Boolean get() = viewState == ViewState.LOADING

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow && position == itemCount - 1)
            R.layout.item_loading
        else
            R.layout.item_image
    }

    fun setViewState(viewState: ViewState?) {
//        if (!isRefresh && itemCount > 0){
//            val isLoadMoreShown = hasExtraRow
//            this.viewState = viewState
//            if (isLoadMoreShown != hasExtraRow){
//                if (isLoadMoreShown){
//                    notifyItemRemoved(super.getItemCount())
//                } else {
//                    notifyItemInserted(super.getItemCount())
//                }
//            }
//        }
    }

    class  DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @ExperimentalTime
        @ExperimentalCoil
        fun bind(item: UnsplashResponse?, listener: OnClickListener) {
            item?.let {
                Log.d("TAG", "id: ${it.id}")
                it.urls?.thumb?.let { url ->
                    itemView.iv_item_image.setImageFromUrl(url, it.id)
                }
                itemView.tv_item_description.text = it.description ?: it.altDescription
                itemView.setOnClickListener { listener.onItemClickListener(item) }
            }
        }
    }

    class LoadingViewHolder(view: View): RecyclerView.ViewHolder(view)

}

interface OnClickListener {
    fun onItemClickListener(item: UnsplashResponse)
}