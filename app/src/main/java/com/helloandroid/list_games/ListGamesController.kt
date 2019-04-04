package com.helloandroid.list_games

import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.App
import com.helloandroid.R
import com.helloandroid.game_pager.GamePagerController
import com.helloandroid.room.AppDatabase
import com.helloandroid.room.Game
import com.helloandroid.room.World
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

val WORLD_KEY = "WORLD_KEY"

class ListGamesController(args: Bundle) : Controller(args), ListGamesContract.Controller {

    lateinit var world: World

    constructor(worldId: Long) : this(Bundle().apply { putLong(WORLD_KEY, worldId) })

    @Inject
    lateinit var view: ListGamesContract.View
    @Inject
    lateinit var db: AppDatabase

    private val gamesSet = TreeSet<Game>(kotlin.Comparator { o1, o2 ->
        val res = o2.time.compareTo(o1.time)
        if(res == 0) {
            return@Comparator o1.name.compareTo(o2.name)
        }
        return@Comparator res
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
        world = db.worldDao().getWorldById(args.getLong(WORLD_KEY))

        gamesSet.addAll(db.gameDao().getAll(world.id, archived = false))
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        val games = db.gameDao().getAll(world.id, archived = false)
            .sortedWith(kotlin.Comparator { o1, o2 ->
                var res = o2.time.compareTo(o1.time)
                if(res == 0) {
                    res = o1.name.compareTo(o2.name)
                }
                return@Comparator res
            })
        this.view.setData(games.toMutableList())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.list_games, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add_game -> {
                view.showAddGameDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(game: Game) {
        val router = parentController?.router ?: router
        router.pushController(RouterTransaction.with(GamePagerController(world.id, game.id)))
    }

    override fun getWorldName(): String {
        return world.name
    }

    override fun createGame(gameName: String) {
        val game = Game(gameName, world.id, Calendar.getInstance().time)
        val id = db.gameDao().insert(game)
        game.id = id
        view.addedAt(0, game)
    }

    override fun archiveGameAt(pos: Int) {
        val game = gamesSet.toList()[pos]
        game.archived = true

        db.gameDao().update(game)
        gamesSet.remove(game)
        view.archivedAt(pos)
    }
}