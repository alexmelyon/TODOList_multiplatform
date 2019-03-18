package com.helloandroid.session

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.helloandroid.MainActivity
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class SessionView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), SessionContract.View {

    @Inject
    lateinit var controller: SessionContract.Controller

    lateinit var listAdapter: SessionDiffsAdapter

    override fun createView(container: ViewGroup): View {
        activity.supportActionBar!!.title = controller.getSessionDatetime()
        // TODO Add SessionItem button
        listAdapter = SessionDiffsAdapter(container.context).apply {
            onItemMinus = { id, type ->
                when (type) {
                    SessionItemType.ITEM_HP -> controller.onHpChanged(id, -1)
                    SessionItemType.ITEM_SKILL -> controller.onSkillChanged(id, -1)
                    SessionItemType.ITEM_THING -> controller.onThingChanged(id, -1)
                }
            }
            onItemPlus = { id, type ->
                when(type) {
                    SessionItemType.ITEM_HP -> controller.onHpChanged(id, +1)
                    SessionItemType.ITEM_SKILL -> controller.onSkillChanged(id, +1)
                    SessionItemType.ITEM_THING -> controller.onThingChanged(id, +1)
                }
            }
            onCommentChanged = { id, comment ->
                controller.onCommentChanged(id, comment)
            }
        }
        return container.context.recyclerView {
            adapter = listAdapter
        }
    }

    override fun setData(items: MutableList<SessionItem>) {
        listAdapter.items = items
    }

    override fun itemChangedAt(pos: Int) {
        listAdapter.notifyItemChanged(pos)
    }

    override fun itemAddedAt(pos: Int, sessionItem: SessionItem) {
        listAdapter.items.add(pos, sessionItem)
        listAdapter.notifyItemInserted(pos)
    }

    override fun itemRemovedAt(pos: Int) {
        listAdapter.notifyItemRemoved(pos)
    }

    override fun showAddHpDialog(characterNames: List<String>) {
        val charactersAdapter = ArrayAdapter<String>(activity, R.layout.select_dialog_singlechoice)
        charactersAdapter.addAll(characterNames)
        val dialog = AlertDialog.Builder(activity)
            .setTitle("Select character")
            .setItems(characterNames.toTypedArray(), DialogInterface.OnClickListener { dialog, which ->
                controller.addHpDiff(which)
            })
            .setCancelable(true)
        dialog.show()
    }

    override fun showAddSkillDialog(characterNames: List<String>) {
        Log.i("JCD", "showAddSkillDialog".toUpperCase())
    }

    override fun showAddThingDialog(characterNames: List<String>) {
        Log.i("JCD", "showAddThingDialog".toUpperCase())
    }

    override fun addComment() {
        Log.i("JCD", "addComment".toUpperCase())
    }

}