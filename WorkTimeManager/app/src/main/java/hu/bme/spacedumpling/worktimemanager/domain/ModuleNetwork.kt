package hu.bme.spacedumpling.worktimemanager.domain

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.bme.spacedumpling.worktimemanager.android.baseUrl
import hu.bme.spacedumpling.worktimemanager.domain.api.WorkTimeManagerAPIService
import hu.bme.spacedumpling.worktimemanager.domain.api.WorkTimeManagerNetworkDatasource
import hu.bme.spacedumpling.worktimemanager.domain.api.WorkTimeManagerNetworkDatasourceImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {


}