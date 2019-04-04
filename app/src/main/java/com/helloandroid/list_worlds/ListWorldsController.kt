package com.helloandroid.list_worlds

import android.content.Context
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.helloandroid.R
import com.helloandroid.room.AppDatabase
import com.helloandroid.room.World
import com.helloandroid.world_pager.WorldPagerController
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

class ListWorldsController : Controller(), ListWorldsContract.Controller {

    @Inject
    lateinit var view: ListWorldsContract.View
    @Inject
    lateinit var db: AppDatabase

    private lateinit var setWorlds: TreeSet<World>

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
            val res = o2.createTime.compareTo(o1.createTime)
            if (res == 0) {
                return@Comparator o1.name.compareTo(o2.name)
            }
            return@Comparator res
        })
        setWorlds.addAll(db.worldDao().getAll(archived = false))
        this.view.setData(setWorlds.toMutableList())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_worlds, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_world -> {
                view.showCreateWorldDialog()
                return true
            }
            R.id.menu_show_tutorial -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(world: World) {
        router.pushController(RouterTransaction.with(WorldPagerController(world.id)))
    }

    override fun createWorld(worldName: String) {
        val world = World(worldName, Calendar.getInstance().time)
        db.worldDao().insert(world)

        setWorlds.add(world)
        view.addedAt(0, world)
    }

    override fun archiveWorldAt(pos: Int) {
        val world = setWorlds.toList()[pos]
        world.archived = true
        db.worldDao().update(world)

        setWorlds.remove(world)
        view.archivedAt(pos)
    }
}
