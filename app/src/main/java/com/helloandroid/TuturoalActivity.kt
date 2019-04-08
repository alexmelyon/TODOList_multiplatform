package com.helloandroid

import android.Manifest
import android.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.hololo.tutorial.library.PermissionStep
import com.hololo.tutorial.library.Step
import com.hololo.tutorial.library.TutorialActivity


class TuturoalActivity : TutorialActivity() {

    val SHARED = "SHARED"
    val TUTORIAL = "TUTORIAL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getSharedPreferences(SHARED, Context.MODE_PRIVATE).getBoolean(TUTORIAL, false)) {
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }

        addFragment(
            Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#ef5350"))
                .setDrawable(R.drawable.ic_input_add)
                .setSummary("This is summary")
                .build()
        )
        addFragment(
            Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#ec407a"))
                .setDrawable(R.drawable.ic_input_add)
                .setSummary("This is summary")
                .build()
        )

        addFragment(
            Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#ab47bc"))
                .setDrawable(R.drawable.ic_input_add)
                .setSummary("This is summary")
                .build()
        )

        addFragment(
            Step.Builder().setTitle("This is header")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#7e57c2")) // int background color
                .setDrawable(R.drawable.ic_input_add) // int top drawable
                .setSummary("This is summary")
                .build()
        )
//         Permission Step
        addFragment(
            PermissionStep.Builder().setTitle("Permissions")
                .setContent("Permission detail")
                .setBackgroundColor(Color.parseColor("#FF0957"))
                .setDrawable(R.drawable.ic_btn_speak_now)
                .setSummary("Continue and learn")
                .setPermissions(arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                .build()
        )
    }

    override fun currentFragmentPosition(p0: Int) {

    }

    override fun finishTutorial() {
        super.finishTutorial()
        getSharedPreferences(SHARED, Context.MODE_PRIVATE).edit().apply {
            putBoolean(TUTORIAL, true)
            apply()
        }
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
