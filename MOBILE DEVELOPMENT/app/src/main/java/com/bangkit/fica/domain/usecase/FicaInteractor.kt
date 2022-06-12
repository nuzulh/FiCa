package com.bangkit.fica.domain.usecase

import com.bangkit.fica.data.indicator.Status
import com.bangkit.fica.data.source.remote.response.PredictResponse
import com.bangkit.fica.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class FicaInteractor(private val repository: AppRepository): FicaUseCase {

    override fun getPredict(
        image: MultipartBody.Part
    ): Flow<Status<PredictResponse>> = repository.getPredict(image)

}