package com.helloandroid.tutorial

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.helloandroid.MainActivity
import com.helloandroid.R
import com.hololo.tutorial.library.Step
import com.hololo.tutorial.library.TutorialActivity


class TutorialActivity : TutorialActivity() {

    private val SHARED = "SHARED"
    private val TUTORIAL = "TUTORIAL"

    companion object {
        const val FORCED_TUTORIAL = "FORCED_TUTORIAL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val forcedTutorial = intent.extras?.getBoolean(FORCED_TUTORIAL) ?: false

        if (!forcedTutorial && getSharedPreferences(SHARED, Context.MODE_PRIVATE).getBoolean(TUTORIAL, false)) {
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }

        addFragment(
            Step.Builder().setTitle("Create worlds")
                .setBackgroundColor(Color.parseColor("#ef5350"))
                .setDrawable(R.drawable.tutorial_1_create_world)
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create games")
                .setBackgroundColor(Color.parseColor("#ec407a"))
                .setDrawable(R.drawable.tutorial_2_create_game)
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create characters")
                .setBackgroundColor(Color.parseColor("#ab47bc"))
                .setDrawable(R.drawable.tutorial_3_create_character)
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create sessions")
                .setBackgroundColor(Color.parseColor("#7e57c2"))
                .setDrawable(R.drawable.tutorial_4_create_session)
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Add HP to characters")
                .setBackgroundColor(Color.parseColor("#5c6bc0"))
                .setDrawable(R.drawable.tutorial_5_add_hp)
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Create skills")
                .setBackgroundColor(Color.parseColor("#42a5f5"))
                .setDrawable(R.drawable.tutorial_6_create_skill)
                .build()
        )
        addFragment(
            Step.Builder().setTitle("Close the session")
                .setBackgroundColor(Color.parseColor("#29b6f6"))
                .setDrawable(R.drawable.tutorial_7_close_session)
                .build()
        )
        addFragment(
            Step.Builder().setTitle("See results")
                .setBackgroundColor(Color.parseColor("#26c6da"))
                .setDrawable(R.drawable.tutorial_8_characters)
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