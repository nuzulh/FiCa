package com.bangkit.fica.presentation.home.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.fica.data.indicator.Status
import com.bangkit.fica.data.source.remote.response.PredictResponse
import com.bangkit.fica.domain.usecase.FicaUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ScanViewModel(
    private val ficaUseCase: FicaUseCase
) : ViewModel() {
    val predictLiveData     = MutableLiveData<PredictResponse>()
    val loadingLiveData     = MutableLiveData<Boolean>()

    fun getPredict(image: MultipartBody.Part){
        viewModelScope.launch {
            ficaUseCase.getPredict(image).collect { response ->
                when(response){
                    is Status.Loading -> {
                        loadingLiveData.value = true
                    }
                    is Status.Success -> {
                        loadingLiveData.value = false
                        predictLiveData.value = response.data!!
                    }
                    is Status.Error -> {
                        loadingLiveData.value = false
                    }
                    is Status.HttpError -> {
                        loadingLiveData.value = false
                    }
                }
            }
        }
    }
}