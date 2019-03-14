package com.helloandroid.list_sessions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.list_games.WORLD_KEY
import ru.napoleonit.talan.di.ControllerInjector
import javax.inject.Inject

val GAME_KEY = "GAME_KEY"

class ListSessionsController(args: Bundle) : Controller(args), ListSessionsContract.Controller {

    @Inject
    lateinit var view: ListSessionsContract.View

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }
    val game = App.instance.games.first { it.worldGroup == world.id && it.id == args.getInt(GAME_KEY) }

    constructor(worldId: Int, gameId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
        putInt(GAME_KEY, gameId)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        this.view.setData(App.instance.gameSessions.filter { it.worldGroup == world.id && it.gameGroup == game.id }.map { it.name })
    }

    override fun onItemClick(pos: Int) {

    }

    override fun getGameName(): String {
        return game.name
    }
}