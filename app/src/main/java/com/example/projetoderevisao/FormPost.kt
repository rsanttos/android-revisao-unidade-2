package com.example.projetoderevisao

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_form_post.*

class FormPost : AppCompatActivity() {

    val postRepository = SQLiteRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_post)

        btnCancelar.setOnClickListener {
            finish()
        }

        btnSalvar.setOnClickListener {
            val title = edtTitle.text
            val body = edtBody.text
            var post : Post = Post(0)
            post.title = title.toString()
            post.body = body.toString()
            postRepository.save(post)
            var newActivity = Intent(this, ListPosts::class.java)
            startActivity(newActivity)
        }
    }
}
