package id.husna.benchpicasso.helpers

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import id.husna.benchpicasso.R
import kotlinx.coroutines.*
import java.io.InputStream
import java.lang.Exception
import java.net.URL
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
    Picasso.get().load(url).into(this, object : Callback {
        override fun onSuccess() {
            Log.d("TAG", "time: ${id}, ${mark.elapsedNow().inMilliseconds}")
        }

        override fun onError(e: Exception?) {
        }
    })
//    Picasso.get()
//        .load(url)
//        .placeholder(R.drawable.ic_image)
//        .error(R.drawable.ic_broken_image)
//        .into(this)
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}