package com.helloandroid.dagger

import android.app.Application
import com.helloandroid.dagger.components.AppComponent
import com.helloandroid.dagger.components.DaggerAppComponent
import com.helloandroid.dagger.modules.AppModule
import com.helloandroid.dagger.modules.ReceiversModule
import com.helloandroid.dagger.modules.UtilsModule

class App : Application() {

    companion object {
        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
//            .utilsModule(UtilsModule())
//            .receiversModule(ReceiversModule())
            .build()
    }
}