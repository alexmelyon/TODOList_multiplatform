package ru.napoleonit.talan.di

import com.bluelinelabs.conductor.Controller
import dagger.android.DispatchingAndroidInjector

interface HasControllerInjector {

    fun controllerInjector(): DispatchingAndroidInjector<Controller>
}