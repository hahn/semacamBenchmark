package id.husna.benchpicasso.network

import id.husna.benchpicasso.network.response.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  ImageBenchmarkBase
 *  Created by hanhan.firmansyah on 16/04/20.
 *
 */
interface ServiceApi {

    @GET("users/wistomsin/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("client_id") clientId: String,
        @Query("per_page") perPage: Int = 8
    ): List<UnsplashResponse>
}