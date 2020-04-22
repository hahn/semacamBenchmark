package id.husna.benchpicasso.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
@Parcelize
data class UnsplashResponse(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("promoted_at")
    val promotedAt: String? = null,
    val width: Int,
    val height: Int,
    val color: String,
    val description: String? = null,
    @SerializedName("alt_description")
    val altDescription: String? = null,
    val urls: Urls? = null,
    val categories: List<String>? = emptyList(),
    val likes: Int,
    val user: User
) : Parcelable {

    @Parcelize
    data class Urls(
        val raw: String? = null,
        val full: String? = null,
        val regular: String? = null,
        val small: String? = null,
        val thumb: String? = null
    ): Parcelable

    @Parcelize
    data class User(
        val id: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        val username: String,
        val name: String
    ) : Parcelable
}