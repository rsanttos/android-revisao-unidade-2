package com.example.projetoderevisao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list_posts.*

class ListPosts : AppCompatActivity() {

    val postRepository = SQLiteRepository(this)
    private var posts = mutableListOf<Post>()
    private var adapter = PostAdapter(posts, this::onPostItemLongClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_posts)

        posts.addAll(postRepository.findAll())

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actions,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_add){
            var newIntent = Intent(this, FormPost::class.java)
            startActivity(newIntent)
            return true
        } else if(id == R.id.action_logout){
            var newActivity = Intent(this, MainActivity::class.java)
            startActivity(newActivity)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun initSwipeDelete(){
        val swipe = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                posts.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(rvPosts)
    }


    fun initRecyclerView(){

        rvPosts.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

        rvPosts.layoutManager = layoutManager

        initSwipeDelete()
    }

    fun onPostItemLongClick(post: Post, position: Int){
        DialogConfirmacao.show(supportFragmentManager, object : DialogConfirmacao.OnBtnListener{
            override fun onConfirm() {
                posts.remove(post)
                postRepository.remove(post)
                adapter.notifyItemRemoved(position)
            }
        })
    }
}
