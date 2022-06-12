package com.bangkit.fica.domain.usecase

import com.bangkit.fica.data.indicator.Status
import com.bangkit.fica.data.source.remote.response.PredictResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FicaUseCase {

    fun getPredict(image: MultipartBody.Part) : Flow<Status<PredictResponse>>

}