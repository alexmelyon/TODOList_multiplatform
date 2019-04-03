package com.helloandroid.list_things

import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.R
import com.helloandroid.Thing
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.room.AppDatabase
import com.helloandroid.room.World
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

class ListThingsController(args: Bundle) : Controller(args), ListThingsContract.Controller {

    @Inject
    lateinit var view: ListThingsContract.View
    @Inject
    lateinit var db: AppDatabase

    lateinit var world: World

    constructor(worldId: Int) : this(Bundle().apply {
        putInt(WORLD_KEY, worldId)
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return view.createView(container)
    }


    override fun onContextAvailable(context: Context) {
        super.onContextAvailable(context)
        ControllerInjector.inject(this)
        world = db.worldDao().getWorldById(args.getInt(WORLD_KEY))
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        val things = App.instance.things.filter { it.worldGroup == world.id }
            .filterNot { it.archived }
            .sortedWith(kotlin.Comparator { o1, o2 ->
                var res = o2.lastUsed.compareTo(o1.lastUsed)
                if(res == 0) {
                    res = o1.name.compareTo(o2.name)
                }
                return@Comparator res
            })
            .toMutableList()
        this.view.setData(things)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.list_things, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add_thing -> {
                view.showAddThingDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun archiveThing(pos: Int, thing: Thing) {
        thing.archived = true

        view.archivedAt(pos)
    }

    override fun createThing(thingName: String) {
        val maxId = App.instance.things.maxBy { it.id }?.id ?: -1
        val thing = Thing(maxId + 1, thingName, world.id, Calendar.getInstance().time)
        App.instance.things.add(thing)

        view.addedAt(0, thing)
    }
}