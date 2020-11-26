package com.example.loginflow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    companion object {
        const val BASE_URL = "https://reqres.in/"
    }
}