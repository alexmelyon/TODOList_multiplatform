package com.helloandroid.preview_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.TwoLineListItem
import org.jetbrains.anko.include
import org.jetbrains.anko.verticalLayout

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            include<TextView>(android.R.layout.simple_list_item_1) {
                text = "simple_list_item_1"
            }
            include<TwoLineListItem>(android.R.layout.simple_list_item_2) {
                text1.text = "simple_list_item_2"
                text2.text = "simple_list_item_2"
            }
        }
    }
}
