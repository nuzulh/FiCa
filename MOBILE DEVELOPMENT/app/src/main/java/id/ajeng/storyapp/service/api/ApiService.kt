package id.ajeng.storyapp.service.api

import id.ajeng.storyapp.service.data.AddStoryResponse
import id.ajeng.storyapp.service.data.StoryResponse
import id.ajeng.storyapp.service.data.LoginRequest
import id.ajeng.storyapp.service.data.LoginResponse
import id.ajeng.storyapp.service.data.register.RegisterRequest
import id.ajeng.storyapp.service.data.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("register")
    suspend fun userRegister(
        @Body body: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun userLogin(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") bearer: String
    ): Response<StoryResponse>

    @Multipart
    @POST("stories")
    suspend fun postStories(
        @Header("Authorization") bearer: String,
        @Part file : MultipartBody.Part,
        @Part("description") description : RequestBody,
    ): Response<AddStoryResponse>
}