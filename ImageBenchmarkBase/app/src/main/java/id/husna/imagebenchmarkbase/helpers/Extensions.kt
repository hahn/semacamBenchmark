package id.husna.imagebenchmarkbase.helpers

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import id.husna.imagebenchmarkbase.R
import kotlinx.coroutines.*
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource


/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *  Copyright © 2020 Global Digital Niaga, PT. All rights reserved.
 *
 */

fun ViewGroup.inflate(@LayoutRes res: Int): View =
    LayoutInflater.from(this.context).inflate(res, this, false)

@ExperimentalTime
@ExperimentalCoroutinesApi
fun ImageView.setImageFromUrl(url: String, id: String) {
    val clock = TimeSource.Monotonic
    val mark = clock.markNow()
    val scope = CoroutineScope(Dispatchers.Main)
    val view = this
    try {
        scope.launch {
            val img = loadImageUrl(url)
            view.setImageDrawable(img)
            Log.d("TAG", "time: ${id}, ${mark.elapsedNow().inMilliseconds}")

        }
    } catch (e: Exception) {
        e.printStackTrace()
        this.setImageResource(R.drawable.ic_launcher_foreground)
    }
}

@ExperimentalCoroutinesApi
suspend fun loadImageUrl(url: String): Drawable = Dispatchers.Default {
    val inputStream: InputStream = URL(url).content as InputStream
    return@Default Drawable.createFromStream(inputStream, url)
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.logd(message: String) {
    Log.d("TAG", message)
}