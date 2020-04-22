package id.husna.benchglide.helpers

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import id.husna.benchglide.R
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource


/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */

fun ViewGroup.inflate(@LayoutRes res: Int): View =
    LayoutInflater.from(this.context).inflate(res, this, false)

@ExperimentalTime
fun ImageView.setImageFromUrl(url: String, id: String) {
    val clock = TimeSource.Monotonic
    val mark = clock.markNow()
    Glide.with(context).load(url).into(object : CustomTarget<Drawable>() {
        override fun onLoadCleared(placeholder: Drawable?) {

        }

        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            this@setImageFromUrl.setImageDrawable(resource)
            Log.d("TAG", "time: ${id}, ${mark.elapsedNow().inMilliseconds}")
        }
    })
//    Glide.with(context)
//        .load(url)
//        .placeholder(R.drawable.ic_image)
//        .error(R.drawable.ic_broken_image)
//        .into(this)
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.logd(message: String) {
    Log.d("TAG", message)
}