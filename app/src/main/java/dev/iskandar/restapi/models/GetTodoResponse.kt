package dev.iskandar.restapi.models

import java.io.Serializable

data class GetTodoResponse(
    val bajarildi: Boolean,
    val batafsil: String,
    val id: Int,
    val oxirgi_muddat: String,
    val sana: String,
    val sarlavha: String,
    val zarurlik: String
) : Serializable