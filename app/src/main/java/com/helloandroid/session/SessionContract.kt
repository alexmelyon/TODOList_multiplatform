package com.helloandroid.session

import android.view.ViewGroup

interface SessionContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<SessionItem>)
    }
    interface Controller {
        fun getSessionDatetime(): String
        fun onHpChanged(id: Int, value: Int)
        fun onSkillChanged(id: Int, value: Int)
        fun onThingChanged(id: Int, value: Int)
        fun onCommentChanged(id: Int, comment: String)
    }
}

enum class SessionItemType {
    ITEM_HP,
    ITEM_SKILL,
    ITEM_THING,
    ITEM_COMMENT
}

class SessionItem(val type: SessionItemType, val id: Int, val title: String, val desc: String, val value: Int, val comment: String = "")