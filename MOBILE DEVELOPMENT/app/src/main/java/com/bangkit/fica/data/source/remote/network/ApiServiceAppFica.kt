package com.bangkit.fica.data.source.remote.network

import com.bangkit.fica.data.source.remote.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceAppFica {
    @Multipart
    @POST("predict")
    suspend fun getPredict(
        @Part image: MultipartBody.Part
    ): PredictResponse
}