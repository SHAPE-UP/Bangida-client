package com.example.shape_up_2022.retrofit

import com.example.shape_up_2022.data.TodoItem
import com.example.shape_up_2022.data.Todorole
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT


interface NetworkServiceTodo {
    @POST("getTodo")
    fun getTodo(
        @Body user: GetTodoReq,
    ): Call<GetTodoRes>

    @POST("registerTodo")
    fun registerTodo(
        @Body user: RegisterTodoReq,
    ): Call<RegisterTodoRes>

    @PUT("editTodo")
    fun editTodo(
        @Body body: EditTodoReq,
    ): Call<EditTodoRes>

    @DELETE("deleteTodo")
    fun deleteTodo(
        @Body body: DeleteTodoReq,
    ): Call<DeleteTodoRes>

}


data class GetTodoReq(val familyID: String, val date: String)
data class GetTodoRes(val success: String, val todoInfo: Array<TodoItem>)

data class RegisterTodoReq(val familyID: String, val date: String,
                           val todowork: String,
                           val todorole: String,  // userID
                           val todotime: Int,
                           val todoref: Int
                           )
data class RegisterTodoRes(val success: String)

data class EditTodoReq(val name: String, val email: String, val password: String)
data class EditTodoRes(val success: String)

data class DeleteTodoReq(val name: String, val email: String, val password: String)
data class DeleteTodoRes(val success: String)

