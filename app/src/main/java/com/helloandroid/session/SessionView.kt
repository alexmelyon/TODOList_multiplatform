package com.helloandroid.session

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import com.helloandroid.MainActivity
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class SessionView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), SessionContract.View {

    @Inject
    lateinit var controller: SessionContract.Controller

    lateinit var listAdapter: SessionDiffsAdapter

    override fun createView(container: ViewGroup): View {
        activity.supportActionBar!!.title = controller.getTitle()
        listAdapter = SessionDiffsAdapter(container.context, editable = controller.isSessionOpen()).apply {
            onItemMinus = { pos, type ->
                when (type) {
                    SessionItemType.ITEM_HP -> controller.onHpChanged(pos, -1)
                    SessionItemType.ITEM_SKILL -> controller.onSkillChanged(pos, -1)
                    SessionItemType.ITEM_THING -> controller.onThingChanged(pos, -1)
                }
            }
            onItemPlus = { pos, type ->
                when (type) {
                    SessionItemType.ITEM_HP -> controller.onHpChanged(pos, +1)
                    SessionItemType.ITEM_SKILL -> controller.onSkillChanged(pos, +1)
                    SessionItemType.ITEM_THING -> controller.onThingChanged(pos, +1)
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
        listAdapter.itemAdded(pos, sessionItem)
    }

    override fun itemRemovedAt(pos: Int) {
        listAdapter.items.removeAt(pos)
        listAdapter.notifyItemRemoved(pos)
    }

    override fun showAddSomethingDialog() {
        AlertDialog.Builder(activity)
            .setItems(arrayOf("Add Healthpoints", "Add Skill", "Add Thing", "Add Comment"), DialogInterface.OnClickListener { dialog, which ->
                controller.onAddItemClicked(which)
            })
            .show()
    }

    override fun showAddHpDialog(characterNames: List<String>) {
        AlertDialog.Builder(activity)
            .setTitle("Select character")
            .setItems(characterNames.toTypedArray(), DialogInterface.OnClickListener { dialog, character ->
                controller.addHpDiff(character)
            })
            .show()
    }

    override fun showAddSkillDialog(characterNames: List<String>, skillNames: List<String>) {
        AlertDialog.Builder(activity)
            .setTitle("Select character")
            .setItems(characterNames.toTypedArray(), DialogInterface.OnClickListener { dialog, character ->
                AlertDialog.Builder(activity)
                    .setTitle("Select skill")
                    .setItems(skillNames.toTypedArray(), DialogInterface.OnClickListener { dialog, skill ->
                        controller.addCharacterSkillDiff(character, skill)
                    }).show()
            }).show()
    }

    override fun showAddThingDialog(characterNames: List<String>, thingNames: List<String>) {
        AlertDialog.Builder(activity)
            .setTitle("Select character")
            .setItems(characterNames.toTypedArray(), DialogInterface.OnClickListener { dialog, character ->
                AlertDialog.Builder(activity)
                    .setTitle("Select thing")
                    .setItems(thingNames.toTypedArray(), DialogInterface.OnClickListener { dialog, thing ->
                        controller.addCharacterThingDiff(character, thing)
                    }).show()
            }).show()
    }

    override fun showAddComment() {
        controller.addCommentDiff()
    }

    override fun showCloseSessionDialog(name: String) {
        AlertDialog.Builder(activity)
            .setTitle("Close this session?")
            .setMessage(name)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                controller.closeSession()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }
}