package com.helloandroid.tutorial

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.helloandroid.MainActivity
import com.helloandroid.R
import com.hololo.tutorial.library.Step
import com.hololo.tutorial.library.TutorialActivity


class TutorialActivity : TutorialActivity() {

    val SHARED = "SHARED"
    val TUTORIAL = "TUTORIAL"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        if (getSharedPreferences(SHARED, Context.MODE_PRIVATE).getBoolean(TUTORIAL, false)) {
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }

        addFragment(
            Step.Builder().setTitle("Create worlds")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#ef5350"))
                .setDrawable(R.drawable.tutorial_1_create_world)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create games")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#ec407a"))
                .setDrawable(R.drawable.tutorial_2_create_game)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create characters")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#ab47bc"))
                .setDrawable(R.drawable.tutorial_3_create_character)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Open sessions")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#7e57c2"))
                .setDrawable(R.drawable.tutorial_4_open_sessions)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create sessions")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#5c6bc0"))
                .setDrawable(R.drawable.tutorial_5_create_session)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Add healthpoints")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#42a5f5"))
                .setDrawable(R.drawable.tutorial_6_add_hp)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create skills")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#29b6f6"))
                .setDrawable(R.drawable.tutorial_7_create_skill)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Add skills")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#26c6da"))
                .setDrawable(R.drawable.tutorial_8_add_skill)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create things")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#26a69a"))
                .setDrawable(R.drawable.tutorial_9_create_thing)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create stories")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#66bb6a"))
                .setDrawable(R.drawable.tutorial_10_create_comment)
                .setSummary("This is summary")
                .build()
        )
    }

    override fun currentFragmentPosition(pos: Int) {
        Log.d("JCD", "POSITION $pos")
    }

    override fun finishTutorial() {
        super.finishTutorial()
        getSharedPreferences(SHARED, Context.MODE_PRIVATE).edit().apply {
            putBoolean(TUTORIAL, true)
            apply()
        }
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}