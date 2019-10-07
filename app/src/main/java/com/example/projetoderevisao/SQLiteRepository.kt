package com.example.projetoderevisao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class SQLiteRepository(ctx: Context) : PostRepository {
    override fun findAll(): List<Post> {
        var sql ="SELECT * FROM $TABLE_NAME"
        var args: Array<String>? = null

        sql += " ORDER BY $COLUMN_TITULO"
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, args)
        val posts = ArrayList<Post>()
        while(cursor.moveToNext()){
            val post = postFromCursor(cursor)
            posts.add(post)
        }
        cursor.close()
        db.close()
        return posts
    }

    private val helper: PostSqlHelper = PostSqlHelper(ctx)

    private fun insert(n : Post){
        val db = helper.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_TITULO, n.title)
            put(COLUMN_BODY, n.body)
        }

        val id = db.insert(TABLE_NAME, null, cv)
        if(id != 1L){
            n.id = id.toInt()
        }
        db.close()
    }


    private fun update(n : Post){
        val db = helper.writableDatabase
        val cv = ContentValues().apply{
            put(COLUMN_TITULO, n.title)
            put(COLUMN_BODY, n.body)
        }

        db.update(TABLE_NAME, cv, "$COLUMN_ID = ?", arrayOf(n.id.toString()))

        db.close()
    }

    override fun save(post: Post) {
        if(post.id == 0){
            insert(post)
        } else {
            update(post)
        }
    }

    override fun remove(vararg posts: Post) {
        val db = helper.writableDatabase
        for(p in posts){
            db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(p.id.toString()))
        }
        db.close()
    }

    override fun findById(id: Long, callback: (Post?) -> Unit) {
        val sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ? "
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, arrayOf(id.toString()))
        val p = if(cursor.moveToNext())postFromCursor(cursor) else null

        callback(p)
    }

    private fun postFromCursor(cursor: Cursor) : Post {
        val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
        val titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
        val body = cursor.getString(cursor.getColumnIndex(COLUMN_BODY))
        val post : Post = Post(id)
        post.title = titulo
        post.body = body
        return post
    }

    override fun search(term: String, callback: (List<Post>) -> Unit) {
        var sql ="SELECT * FROM $TABLE_NAME"
        var args: Array<String>? = null

        if(term.isNotEmpty()){
            sql += " WHERE $COLUMN_TITULO LIKE ?"
            args = arrayOf("%$term%")
        }

        sql += " ORDER BY $COLUMN_TITULO"
        val db = helper.writableDatabase
        val cursor = db.rawQuery(sql, args)
        val posts = ArrayList<Post>()
        while(cursor.moveToNext()){
            val post = postFromCursor(cursor)
            posts.add(post)
        }
        cursor.close()
        db.close()
        callback(posts)
    }
}