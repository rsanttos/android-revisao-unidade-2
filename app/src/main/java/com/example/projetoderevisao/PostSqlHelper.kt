package com.example.projetoderevisao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PostSqlHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITULO TEXT NOT NULL," +
                "$COLUMN_BODY TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}