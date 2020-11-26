package com.example.loginflow.di

import com.example.loginflow.MyApplication
import com.example.loginflow.data.coupon.CouponCodeApi
import com.example.loginflow.data.login.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppComponent {
    @Singleton
    @Provides
    fun provideRetrofit() =
        Retrofit.Builder()
            .baseUrl(MyApplication.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() as Retrofit

    @Singleton
    @Provides
    fun providesLoginApi(retrofit: Retrofit) =
        retrofit.create(LoginApi::class.java) as LoginApi

    @Singleton
    @Provides
    fun providesCouponApi(retrofit: Retrofit) =
        retrofit.create(CouponCodeApi::class.java) as CouponCodeApi
}