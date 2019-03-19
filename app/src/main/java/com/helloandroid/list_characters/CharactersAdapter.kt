package com.helloandroid.list_characters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.helloandroid.R
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView

class CharactersAdapter(val context: Context, val onLongTapListener: (Int, CharacterItem) -> Unit) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    var items = mutableListOf<CharacterItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.context.linearLayout {
            orientation = LinearLayout.VERTICAL
            setPadding(8, 8, 8, 0)

            textView("Character name") {
                id = R.id.character_name
                textSize = 20F
                textColor = Color.BLACK
                typeface = Typeface.DEFAULT_BOLD
            }
            textView {
                id = R.id.character_hp
                textColor = Color.BLACK
            }
            textView("Skills:") {
                typeface = Typeface.DEFAULT_BOLD
                textColor = Color.BLACK
            }
            textView {
                id = R.id.character_skills
                textColor = Color.BLACK
            }
            textView("Items:") {
                typeface = Typeface.DEFAULT_BOLD
                textColor = Color.BLACK
            }
            textView {
                id = R.id.character_things
                textColor = Color.BLACK
            }
        }
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.hp.text = "HP " + items[position].hp
        holder.skills.text = items[position].skills.fold("") { total, next -> "$total\n${next.first} ${next.second}"}
        holder.things.text = items[position].things.fold("") { total, next -> "$total\n${next.first} ${next.second}"}
        holder.itemView.setOnLongClickListener {
            val correctPosition = holder.adapterPosition
            onLongTapListener(correctPosition, items[position])
            return@setOnLongClickListener true
        }
    }

    fun adddedAt(index: Int, item: CharacterItem) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    fun removedAt(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.character_name)
        val hp = view.findViewById<TextView>(R.id.character_hp)
        val skills = view.findViewById<TextView>(R.id.character_skills)
        val things = view.findViewById<TextView>(R.id.character_things)
    }
}