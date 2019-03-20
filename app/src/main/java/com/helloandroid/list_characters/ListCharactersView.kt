package com.helloandroid.list_characters

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.helloandroid.MainActivity
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class ListCharactersView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), ListCharactersContract.View {

    @Inject
    lateinit var controller: ListCharactersContract.Controller

    lateinit var charactersAdapter: CharactersAdapter

    override fun createView(container: ViewGroup): View {
        charactersAdapter = CharactersAdapter(activity) { pos, item ->
            AlertDialog.Builder(activity)
                .setTitle("Archive character?")
                .setMessage(item.name)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    controller.archiveCharacter(pos, item)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
        }
        return container.context.recyclerView {
            adapter = charactersAdapter
        }
    }

    override fun setData(items: MutableList<CharacterItem>) {
        charactersAdapter.items = items
    }

    override fun showAddCharacterDialog() {
        val editText = EditText(activity)
        AlertDialog.Builder(activity)
            .setTitle("Character name:")
            .setView(editText)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                controller.createCharacter(editText.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

    override fun addedAt(index: Int, item: CharacterItem) {
        charactersAdapter.adddedAt(index, item)
    }

    override fun archiveddAt(pos: Int) {
        charactersAdapter.removedAt(pos)
    }
}