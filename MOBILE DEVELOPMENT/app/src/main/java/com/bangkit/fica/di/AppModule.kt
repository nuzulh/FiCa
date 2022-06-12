package com.bangkit.fica.di

import com.bangkit.fica.Constants
import com.bangkit.fica.data.source.remote.RemoteDataSource
import com.bangkit.fica.data.source.remote.network.ApiServiceAppFica
import com.bangkit.fica.data.source.repository.AppRepositoryImplementation
import com.bangkit.fica.domain.repository.AppRepository
import com.bangkit.fica.domain.usecase.FicaInteractor
import com.bangkit.fica.domain.usecase.FicaUseCase
import com.bangkit.fica.presentation.home.fragment.ScanViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiServiceAppFica::class.java)
    }
}

val useCaseModule = module {
    single<FicaUseCase> { FicaInteractor(get()) }
}

val viewModelModule = module {
    viewModel { ScanViewModel(get()) }
}

val repositoryModule = module {
    single {
        RemoteDataSource(
            get()
        )
    }
    single<AppRepository> {
        AppRepositoryImplementation(
            get()
        )
    }
}
