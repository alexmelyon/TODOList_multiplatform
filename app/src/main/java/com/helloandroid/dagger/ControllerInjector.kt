package ru.napoleonit.talan.di

import com.bluelinelabs.conductor.Controller

object ControllerInjector {

    fun inject(controller: Controller) {
        val activity = controller.activity
        if (activity == null) {
            throw IllegalStateException("Controller $controller is not available activity")
        }
        if (activity !is HasControllerInjector) {
            throw IllegalStateException("Activity of controller $controller is not `HasControllerInjector`")
        }
        activity.controllerInjector().inject(controller)
    }
}