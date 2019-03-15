package com.helloandroid.session

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.helloandroid.MainActivity
import com.helloandroid.ui.RecyclerStringAdapter
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import javax.inject.Inject

class SessionView @Inject constructor(val activity: MainActivity) : _FrameLayout(activity), SessionContract.View {

    @Inject
    lateinit var controller: SessionContract.Controller

    lateinit var listAdapter: RecyclerStringAdapter

    override fun createView(container: ViewGroup): View {
        activity.supportActionBar!!.title = controller.getSessionDatetime()
        // TODO + button
        listAdapter = RecyclerStringAdapter(container.context) { pos ->
            Log.i("JCD", "CLICK $pos")
        }
        return container.context.recyclerView {
            adapter = listAdapter
        }
    }

}