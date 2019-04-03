package com.helloandroid.list_skills

import android.content.Context
import android.os.Bundle
import android.view.*
import com.bluelinelabs.conductor.Controller
import com.helloandroid.App
import com.helloandroid.R
import com.helloandroid.Skill
import com.helloandroid.list_games.WORLD_KEY
import com.helloandroid.room.AppDatabase
import com.helloandroid.room.World
import ru.napoleonit.talan.di.ControllerInjector
import java.util.*
import javax.inject.Inject

class ListSkillsController(args: Bundle) : Controller(args), ListSkillsContract.Controller {

    @Inject
    lateinit var view: ListSkillsContract.View
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
        val worlds = App.instance.skills.filter { it.worldGroup == world.id }
            .filterNot { it.archived }
            .sortedWith(kotlin.Comparator { o1, o2 ->
                var res = o2.lastUsed.compareTo(o1.lastUsed)
                if(res == 0) {
                    res =  o1.name.compareTo(o2.name)
                }
                return@Comparator res
            })
            .toMutableList()
        this.view.setData(worlds)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.list_skills, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add_skill -> {
                view.showAddSkillDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun archiveSkill(pos: Int, skill: Skill) {
        skill.archived = true

        view.archivedAt(pos)
    }

    override fun createSkill(skillName: String) {
        val maxId = App.instance.skills.maxBy { it.id }?.id ?: -1
        val skill = Skill(maxId + 1, skillName, world.id, Calendar.getInstance().time)
        App.instance.skills.add(skill)

        view.addedAt(0, skill)
    }
}