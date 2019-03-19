package com.helloandroid.list_games

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.App
import com.helloandroid.list_sessions.ListSessionsController
import ru.napoleonit.talan.di.ControllerInjector
import javax.inject.Inject

val WORLD_KEY = "WORLD_KEY"

class ListGamesController(args: Bundle) : Controller(args), ListGamesContract.Controller {

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }

    constructor(worldId: Int) : this(Bundle().apply { putInt(WORLD_KEY, worldId) })

    @Inject
    lateinit var view: ListGamesContract.View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(App.instance.games.filter { it.worldGroup == world.id }.map { it.name }.toMutableList())
    }

    override fun onItemClick(pos: Int) {
        router.pushController(RouterTransaction.with(ListSessionsController(world.id, App.instance.gameSessions[pos].id)))
    }

    override fun getWorldName(): String {
        return world.name
    }
}