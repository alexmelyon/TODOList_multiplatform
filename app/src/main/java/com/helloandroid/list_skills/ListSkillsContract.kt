package com.helloandroid.list_skills

import android.view.ViewGroup
import com.helloandroid.room.Skill

interface ListSkillsContract {

    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<Skill>)
        fun archivedAt(pos: Int)
        fun showAddSkillDialog()
        fun addedAt(pos: Int, skill: Skill)
    }

    interface Controller {
        fun archiveSkill(pos: Int, skill: Skill)
        fun createSkill(skillName: String)
    }
}
