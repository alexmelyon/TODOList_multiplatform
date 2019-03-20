package com.helloandroid.list_worlds

import android.content.Context
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.App
import com.helloandroid.R
import com.helloandroid.World
import com.helloandroid.list_games.ListGamesController
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

class ListWorldsController : Controller(), ListWorldsContract.Controller {

    @Inject
    lateinit var view: ListWorldsContract.View

    private lateinit var setWorlds : TreeSet<World>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        setHasOptionsMenu(true)
        return view.createView(container)
    }

    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        setWorlds = TreeSet(kotlin.Comparator { o1, o2 ->
            val res = o2.time.compareTo(o1.time)
            if(res == 0) {
                return@Comparator o1.name.compareTo(o2.name)
            }
            return@Comparator res
        })
        setWorlds.addAll(App.instance.worlds.filterNot { it.archived })
        this.view.setData(setWorlds.toMutableList())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_worlds_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.world_add -> view.showCreateWorldDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(pos: Int) {
        router.pushController(RouterTransaction.with(ListGamesController(App.instance.worlds[pos].id)))
    }

    override fun createWorld(worldName: String) {
        val maxid = App.instance.worlds.maxBy { it.id }?.id ?: -1
        val world = World(maxid + 1, worldName, Calendar.getInstance().time)
        App.instance.worlds.add(world)

        setWorlds.add(world)
        view.addedAt(0, world)
    }

    override fun archiveWorldAt(pos: Int) {
        val world = setWorlds.toList()[pos]
        world.archived = true

        setWorlds.remove(world)
        view.archivedAt(pos)
    }
}
