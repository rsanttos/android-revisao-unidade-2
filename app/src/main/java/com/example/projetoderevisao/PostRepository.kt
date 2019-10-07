package com.example.projetoderevisao

interface PostRepository {
    fun save(post: Post)
    fun remove(vararg posts: Post)
    fun findById(id: Long, callback: (Post?) -> Unit)
    fun search(term: String, callback: (List<Post>) -> Unit)
    fun findAll() : List<Post>
}