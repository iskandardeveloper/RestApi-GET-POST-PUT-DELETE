package dev.iskandar.restapi.retrofit

import dev.iskandar.restapi.models.GetTodoResponse
import dev.iskandar.restapi.models.PostRequestTodo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("plan")
    fun getAllToDo(): Call<List<GetTodoResponse>>

    @POST("plan/")
    fun addToDo(@Body postRequestTodo: PostRequestTodo): Call<GetTodoResponse>

    @DELETE("plan/{id}/")
    fun deleteTodo(@Path("id") id: Int): Call<Any>

    @PUT("plan/{id}/")
    fun updateTodo(
        @Path("id") id: Int, @Body postRequestToDo: PostRequestTodo
    ): Call<GetTodoResponse>
}