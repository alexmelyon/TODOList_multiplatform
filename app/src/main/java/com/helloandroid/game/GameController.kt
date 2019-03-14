package com.helloandroid.game

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.world.WorldProvider
import ru.napoleonit.talan.di.ControllerInjector
import javax.inject.Inject

class GameController : Controller(), GameContract.Controller {

    @Inject
    lateinit var view: GameContract.View
    @Inject
    lateinit var worldProvider: WorldProvider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        Log.i("JCD", "WORLD ${worldProvider.world}")
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(App.instance.games.filter { it.worldGroup == worldProvider.world.id }.map { it.name })
    }

    override fun onItemClick(pos: Int) {

    }
}