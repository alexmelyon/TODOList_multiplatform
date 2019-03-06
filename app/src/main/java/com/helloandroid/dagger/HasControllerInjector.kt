package com.helloandroid.dagger

import com.bluelinelabs.conductor.Controller
import dagger.android.DispatchingAndroidInjector

interface HasControllerInjector {

    fun controllerInjector(): DispatchingAndroidInjector<Controller>
}