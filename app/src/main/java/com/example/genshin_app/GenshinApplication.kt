package com.example.genshin_app

import android.app.Application
import com.example.genshin_app.data.container.AppContainer
import com.example.genshin_app.data.container.GenshinContainer
import com.example.genshin_app.data.network.TokenManager

class GenshinApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
        container = GenshinContainer()
    }
}