package com.helloandroid

import android.app.Application
import com.helloandroid.appcomponent.AppComponent
import com.helloandroid.appcomponent.AppModule
import com.helloandroid.appcomponent.DaggerAppComponent
import com.helloandroid.coffeeComponent.CoffeeComponent
import com.helloandroid.coffeeComponent.DaggerCoffeeComponent
import com.helloandroid.sugarComponent.DaggerSugarComponent
import com.helloandroid.sugarComponent.SugarComponent
import com.helloandroid.teaComponent.DaggerTeaComponent
import com.helloandroid.teaComponent.TeaComponent

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var appComponent: AppComponent
    lateinit var coffeeComponent: CoffeeComponent
    lateinit var teaComponent: TeaComponent
    lateinit var sugarComponent: SugarComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(instance))
            .build()
        coffeeComponent = DaggerCoffeeComponent.builder()
            .appComponent(appComponent)
            .build()
        teaComponent = DaggerTeaComponent.builder()
            .appComponent(appComponent)
            .build()
        sugarComponent = DaggerSugarComponent.builder()
            .build()
    }
}