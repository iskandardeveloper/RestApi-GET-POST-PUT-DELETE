package dev.iskandar.restapi.models

data class PostRequestTodo(
    val bajarildi: Boolean,
    val batafsil: String,
    val oxirgi_muddat: String,
    val sarlavha: String,
    val zarurlik: String
)