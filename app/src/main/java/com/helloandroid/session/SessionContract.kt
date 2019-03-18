package com.helloandroid.session

import android.view.ViewGroup
import java.util.*

interface SessionContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<SessionItem>)
        fun itemChangedAt(pos: Int)
        fun itemAddedAt(pos: Int, sessionItem: SessionItem)
        fun itemRemovedAt(pos: Int)
        fun showAddHpDialog(characterNames: List<String>)
        fun showAddSkillDialog(characterNames: List<String>)
        fun showAddThingDialog(characterNames: List<String>)
        fun addComment()
    }
    interface Controller {
        fun getSessionDatetime(): String
        fun onHpChanged(pos: Int, value: Int)
        fun onSkillChanged(pos: Int, value: Int)
        fun onThingChanged(pos: Int, value: Int)
        fun onCommentChanged(pos: Int, comment: String)
        fun addHpDiff(which: Int)
    }
}

enum class SessionItemType {
    ITEM_HP,
    ITEM_SKILL,
    ITEM_THING,
    ITEM_COMMENT
}

class SessionItem(val id: Int, val time: Date, val type: SessionItemType, val title: String, val desc: String, var value: Int, val characterId: Int, var comment: String = "", var index: Int = -1)