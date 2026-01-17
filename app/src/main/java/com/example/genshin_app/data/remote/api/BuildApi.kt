package com.example.genshin_app.data.remote.api


import com.example.genshin_app.data.remote.dto.BuildDto
import com.example.genshin_app.data.remote.dto.BuildRequest
import com.example.genshin_app.data.remote.dto.SimpleResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface BuildApi {
    @GET("/characters/{slug}/builds")
    suspend fun getBuilds(@Path("slug") slug: String): List<BuildDto>


    @POST("/characters/{slug}/builds")
    suspend fun createBuild(@Path("slug") slug: String, @Body req: BuildRequest): SimpleResponse


    @PUT("/builds/{id}")
    suspend fun updateBuild(@Path("id") id: Int, @Body req: BuildRequest): SimpleResponse


    @DELETE("/builds/{id}")
    suspend fun deleteBuild(@Path("id") id: Int): SimpleResponse
}