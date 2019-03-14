package com.helloandroid.world

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.App
import com.helloandroid.game.GameController
import ru.napoleonit.talan.di.ControllerInjector
import javax.inject.Inject


class WorldController : Controller(), WorldContract.Controller {

    @Inject
    lateinit var view: WorldContract.View
    @Inject
    lateinit var worldProvider: WorldProvider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(App.instance.worlds.map { it.name })
    }

    override fun onItemClick(pos: Int) {
        worldProvider.world = App.instance.worlds[pos]
        router.pushController(RouterTransaction.with(GameController()))

//        val args = mutableMapOf<String, Any?>()
//        if (worldId == null) {
//            args += "world" to pos
//        } else {
//            args += "world" to worldId
//            if (gameId == null) {
//                args += "game" to pos
//            } else {
//                args += "game" to gameId
//                if (sessionId == null) {
//                    args += "session" to pos
//                } else {
//                    args += "session" to sessionId
//                }
//            }
//        }
//        ContextCompat.startActivity(intentFor<MainActivity>(*args.toList().toTypedArray()))
    }

}
