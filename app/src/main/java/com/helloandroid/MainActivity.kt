package com.helloandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.helloandroid.dagger.MyApp
import dagger.Lazy
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class MainActivity : AppCompatActivity() {

    @Inject
    @Named("SingleThread")
    lateinit var singleExecutor: Executor
    @Inject
    @Named("MultiThread")
    lateinit var multiExecutor: Executor
    @Inject
    @Named("SingleThread")
    lateinit var singleExecutorProvider: Provider<Executor>
    @Inject
    @Named("MultiThread")
    lateinit var multiExecutorLazy: Lazy<Executor>
    @Inject
    @Named("MultiThread")
    lateinit var multiExecutorLazyCopy: Lazy<Executor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.instance.appComponent.inject(this)
        setContentView(R.layout.activity_main)

        // Two different objects
        val singleExecutor = singleExecutorProvider.get()
        val singleExecutor2 = singleExecutorProvider.get()

        // Two equal objects
        val multiExecutor = multiExecutorLazy.get()
        val multiExecutor2 = multiExecutorLazy.get()
        // Another one different object
        val multiExecutor3 = multiExecutorLazyCopy.get()
    }
}
