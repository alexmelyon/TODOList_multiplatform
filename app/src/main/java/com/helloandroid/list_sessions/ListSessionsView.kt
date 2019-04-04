package com.helloandroid.list_sessions

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.helloandroid.MainActivity
import com.helloandroid.R
import com.helloandroid.room.GameSession
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class ListSessionsView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), ListSessionsContract.View {

    @Inject
    lateinit var controller: ListSessionsContract.Controller

    lateinit var sessionsAdapter: RecyclerStringAdapter<GameSession>

    override fun createView(container: ViewGroup): View {
        activity.supportActionBar!!.title = controller.getGameName()
        sessionsAdapter = RecyclerStringAdapter(container.context, R.layout.simple_list_item_2_with_header) { pos, session ->
            controller.onItemClick(session)
        }
        sessionsAdapter.onGetDescriptionValue = { pos ->
            controller.getDescription(pos)
        }
        sessionsAdapter.onGetHeaderValue = { pos ->
            controller.getHeader(pos)
        }
        sessionsAdapter.onItemLongclickListener = { pos, session ->
            AlertDialog.Builder(activity)
                .setTitle("Archive session?")
                .setMessage(session.name)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    controller.archiveSession(pos, session)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
        }
        return container.context.recyclerView {
            adapter = sessionsAdapter
        }
    }

    override fun setData(items: MutableList<GameSession>) {
        sessionsAdapter.items = items
    }

    override fun showCreateSessionDialog() {
        val editText = EditText(activity)
        AlertDialog.Builder(activity)
            .setTitle("Session name:")
            .setView(editText)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                controller.createSession(editText.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

    override fun addedAt(pos: Int, session: GameSession) {
        sessionsAdapter.itemAddedAt(pos, session)
    }

    override fun archivedAt(pos: Int) {
        sessionsAdapter.itemRemovedAt(pos)
    }
}