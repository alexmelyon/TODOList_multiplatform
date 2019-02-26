package com.helloandroid

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

// Access property for Context
val Context.database: AnkoSQLiteOpenHelper
    get() = AnkoSQLiteOpenHelper.getInstance(getApplicationContext())

class AnkoSQLiteOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {

    companion object {
        private var instance: AnkoSQLiteOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): AnkoSQLiteOpenHelper {
            if(instance == null) {
                instance = AnkoSQLiteOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("User", true,
            "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            "name" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("User", true)
    }
}