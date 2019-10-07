package com.example.projetoderevisao

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(
    private val posts : List<Post>,
    private val callback : (Post, Int) -> Unit
) : RecyclerView.Adapter<PostAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_post,parent, false)

        val vh = VH(v)

        vh.itemView.setOnLongClickListener {
            val post = posts[vh.adapterPosition]
            callback(post, vh.adapterPosition)
            true
        }

        return vh
    }

    override fun getItemCount(): Int  = posts.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.txtTitle.text = posts[position].title
        holder.txtBody.text = posts[position].body
        holder.txtId.text = posts[position].id.toString()
    }

    class  VH(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtId: TextView = itemView.txtId
        val txtTitle: TextView = itemView.txtTitle
        val txtBody: TextView = itemView.txtBody
    }

}