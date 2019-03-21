package com.helloandroid.list_things

import android.view.ViewGroup
import com.helloandroid.Thing

interface ListThingsContract {
    interface View {
        fun createView(container: ViewGroup): android.view.View
        fun setData(items: MutableList<Thing>)
        fun archivedAt(pos: Int)
        fun showAddThingDialog()
        fun addedAt(pos: Int, thing: Thing)
    }

    interface Controller {
        fun archiveThing(pos: Int, thing: Thing)
        fun createThing(thingName: String)
    }
}
