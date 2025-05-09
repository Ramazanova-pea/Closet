package ru.fan_of_stars.closet

import android.app.Application
import com.fanofstars.data.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ClosetApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ClosetApp)
            modules(appModule)
        }
    }
}