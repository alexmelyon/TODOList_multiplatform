package com.helloandroid.list_things

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.helloandroid.MainActivity
import com.helloandroid.room.Thing
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class ListThingsView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), ListThingsContract.View {

    @Inject
    lateinit var controller: ListThingsContract.Controller

    private lateinit var thingsAdapter: RecyclerStringAdapter<Thing>

    override fun createView(container: ViewGroup): View {
        thingsAdapter = RecyclerStringAdapter(container.context)
        thingsAdapter.onItemLongclickListener = { pos, thing ->
            AlertDialog.Builder(activity)
                .setTitle("Archive thing?")
                .setMessage(thing.name)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    controller.archiveThing(pos, thing)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
        }

        return container.context.recyclerView {
            adapter = thingsAdapter
        }
    }

    override fun setData(items: MutableList<Thing>) {
        thingsAdapter.items = items
    }

    override fun archivedAt(pos: Int) {
        thingsAdapter.itemRemovedAt(pos)
    }

    override fun showAddThingDialog() {
        val editText = EditText(activity)
        AlertDialog.Builder(activity)
            .setTitle("Thing name:")
            .setView(editText)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                controller.createThing(editText.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

    override fun addedAt(pos: Int, thing: Thing) {
        thingsAdapter.itemAddedAt(pos, thing)
    }
}