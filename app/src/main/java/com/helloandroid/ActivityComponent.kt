package com.helloandroid

import android.support.v7.app.AppCompatActivity
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun activity(): AppCompatActivity
}

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity {
        return activity
    }
}