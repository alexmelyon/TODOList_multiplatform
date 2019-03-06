package com.helloandroid.dagger;

import com.bluelinelabs.conductor.Controller;
import dagger.MapKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@MapKey
@Target({ElementType.METHOD})
public @interface ControllerKey {
    Class<? extends Controller> value();
}