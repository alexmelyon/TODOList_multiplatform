package com.helloandroid

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import com.helloandroid.home.HomeControllerModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap


@Component(modules = arrayOf(ActivityModule::class))
interface MainActivityComponent {
    fun activity(): AppCompatActivity
    fun inject(activity: MainActivity)
}

@Subcomponent(modules = [HomeControllerModule::class])
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [(MainActivitySubComponent::class)])
abstract class ActivityModule(val activity: AppCompatActivity) {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    abstract fun bindActivity(mainActivity: MainActivity): AppCompatActivity

//    @Provides
//    fun provideActivity(): AppCompatActivity {
//        return activity
//    }
}