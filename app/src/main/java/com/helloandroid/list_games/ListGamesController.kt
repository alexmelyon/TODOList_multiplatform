package com.helloandroid.list_games

import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.App
import com.helloandroid.Game
import com.helloandroid.R
import com.helloandroid.game_pager.GamePagerController
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

val WORLD_KEY = "WORLD_KEY"

// TODO Pager Skills, Items

class ListGamesController(args: Bundle) : Controller(args), ListGamesContract.Controller {

    val world = App.instance.worlds.first { it.id == args.getInt(WORLD_KEY) }

    constructor(worldId: Int) : this(Bundle().apply { putInt(WORLD_KEY, worldId) })

    private val gamesSet = TreeSet<Game>(kotlin.Comparator { o1, o2 ->
        val res = o2.time.compareTo(o1.time)
        if(res == 0) {
            return@Comparator o1.name.compareTo(o2.name)
        }
        return@Comparator res
    })

    @Inject
    lateinit var view: ListGamesContract.View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setHasOptionsMenu(true)
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)

        gamesSet.addAll(App.instance.games.filterNot { it.archived })
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        val games = App.instance.games.filter { it.worldGroup == world.id }
            .filterNot { it.archived }
        this.view.setData(games.toList().map { it.name }.toMutableList())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_games_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.game_add -> view.showAddGameDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(pos: Int) {
        router.pushController(RouterTransaction.with(GamePagerController(world.id, App.instance.gameSessions[pos].id)))
    }

    override fun getWorldName(): String {
        return world.name
    }

    override fun createGame(gameName: String) {
        val maxId = App.instance.games.maxBy { it.id }?.id ?: -1
        val game = Game(maxId + 1, gameName, world.id, Calendar.getInstance().time)
        App.instance.games.add(game)
        view.addedAt(0, gameName)
    }

    override fun archiveGameAt(pos: Int) {
        val game = gamesSet.toList()[pos]
        game.archived = true

        gamesSet.remove(game)
        view.archivedAt(pos)
    }
}