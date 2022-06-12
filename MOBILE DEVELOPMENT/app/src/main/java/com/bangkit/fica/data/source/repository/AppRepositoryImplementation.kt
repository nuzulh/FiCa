package com.bangkit.fica.data.source.repository

import com.bangkit.fica.data.indicator.Status
import com.bangkit.fica.data.source.remote.RemoteDataSource
import com.bangkit.fica.data.source.remote.network.ApiResponse
import com.bangkit.fica.data.source.remote.response.PredictResponse
import com.bangkit.fica.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class AppRepositoryImplementation(
    private val remoteDataSource: RemoteDataSource
) : AppRepository {

    override fun getPredict(image: MultipartBody.Part): Flow<Status<PredictResponse>> =
        flow {
            emit(Status.Loading())
            when(val apiResponse =
                remoteDataSource.getPredict(image).first()){
                is ApiResponse.Success -> {
                    emit(Status.Success(apiResponse.data))
                }
                is ApiResponse.Error -> {
                    emit(Status.Error(apiResponse.errorMessage))
                }
                is ApiResponse.HttpError -> {
                    emit(Status.HttpError(apiResponse.httpError))
                }
                else -> {
                    emit(Status.Error("Unexpected Error getPredict"))
                }
            }
        }

}