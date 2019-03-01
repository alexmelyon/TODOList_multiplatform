package com.helloandroid.world

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.game.GameController
import javax.inject.Inject


class WorldController : Controller(), WorldContract.Controller {

    private lateinit var view: WorldContract.View

    @Inject
    override fun setView(view: WorldContract.View) {
        this.view = view
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onItemClick(pos: Int) {
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
