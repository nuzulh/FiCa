package com.bangkit.fica.data.source.remote

import com.bangkit.fica.data.source.remote.network.ApiResponse
import com.bangkit.fica.data.source.remote.network.ApiServiceAppFica
import com.bangkit.fica.data.source.remote.response.PredictResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import retrofit2.HttpException

class RemoteDataSource(
    private val apiServiceAppFica: ApiServiceAppFica
    ) {

    suspend fun getPredict(image: MultipartBody.Part): Flow<ApiResponse<PredictResponse>> {
        return flow {
            try {
                val response = apiServiceAppFica.getPredict(image = image)
                emit(ApiResponse.Success(response))
            } catch (he: HttpException) {
                emit(ApiResponse.HttpError(he))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn((Dispatchers.IO))
    }
}