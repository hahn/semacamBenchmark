package id.husna.benchcoil.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.annotation.ExperimentalCoil
import coil.api.load
import coil.transition.CrossfadeTransition
import id.husna.benchcoil.R
import kotlin.time.ExperimentalTime
import kotlin.time.MonoClock
import kotlin.time.TimeSource


/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */

fun ViewGroup.inflate(@LayoutRes res: Int): View =
    LayoutInflater.from(this.context).inflate(res, this, false)

@ExperimentalTime
@ExperimentalCoil
fun ImageView.setImageFromUrl(url: String, id: String) {
    val clock = TimeSource.Monotonic
    val mark = clock.markNow()
    Coil.load(context, url){
        target { drawable ->
            this@setImageFromUrl.setImageDrawable(drawable)
            Log.d("TAG", "time: ${id}, ${mark.elapsedNow().inMilliseconds}")
        }
    }
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}