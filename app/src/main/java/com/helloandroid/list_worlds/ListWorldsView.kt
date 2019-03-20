package com.helloandroid.list_worlds

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.EditText
import com.helloandroid.MainActivity
import com.helloandroid.R
import com.helloandroid.World
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout
import javax.inject.Inject

class ListWorldsView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), ListWorldsContract.View {

    @Inject
    lateinit var controller: ListWorldsContract.Controller

    lateinit var worldsView: RecyclerView
    lateinit var worldsAdapter: RecyclerStringAdapter<World>

    override fun createView(container: ViewGroup) = container.context.verticalLayout {
        activity.supportActionBar!!.title = container.context.getString(R.string.app_name)
        worldsAdapter = RecyclerStringAdapter(container.context) { pos ->
            controller.onItemClick(pos)
        }
        worldsAdapter.onItemLongclickListener = { pos, world ->
            AlertDialog.Builder(activity)
                .setTitle("Archive world?")
                .setMessage(world.name)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    controller.archiveWorldAt(pos)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
        }
        worldsView = recyclerView {
            adapter = worldsAdapter
        }.lparams(matchParent, matchParent)
    }

    override fun setData(items: MutableList<World>) {
        worldsAdapter.items = items
    }

    override fun addedAt(i: Int, world: World) {
        worldsAdapter.itemAddedAt(i, world)
    }

    override fun archivedAt(pos: Int) {
        worldsAdapter.itemRemovedAt(pos)
    }

    override fun showCreateWorldDialog() {
        val editText = EditText(activity)
        AlertDialog.Builder(activity)
            .setTitle("World name:")
            .setView(editText)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                controller.createWorld(editText.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }
}