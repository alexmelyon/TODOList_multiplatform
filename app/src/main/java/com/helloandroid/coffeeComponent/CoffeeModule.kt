package com.helloandroid.coffeeComponent

import android.content.Context
import com.helloandroid.sugarComponent.ISugarService
import com.helloandroid.sugarComponent.WhiteSugarImpl
import dagger.Module
import dagger.Provides

@Module
class CoffeeModule {

    @Provides
    fun provideCoffeeService(context: Context): ICoffeeService {
        return CoffeeService(context)
    }

    @Provides
    fun provideSugar(): ISugarService {
        return WhiteSugarImpl()
    }
}