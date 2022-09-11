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

    @POST("doneTodo")
    fun doneTodo(
        @Body body: DoneTodoReq,
    ): Call<DoneTodoRes>

    /*
    @PUT("editTodo")
    fun editTodo(
        @Body body: EditTodoReq,
    ): Call<EditTodoRes>

    @DELETE("deleteTodo")
    fun deleteTodo(
        @Body body: DeleteTodoReq,
    ): Call<DeleteTodoRes>
    */
}


data class GetTodoReq(val familyID: String, val date: String)
data class GetTodoRes(val success: String, val todoInfo: Array<TodoItem>)

data class RegisterTodoReq(val familyID: String, val date: String,
                           val todowork: String,
                           val todorole: String = "",  // userID
                           val todotime: Int?,
                           val todoref: Int = 0
                           )
data class RegisterTodoRes(val success: String)

data class DoneTodoReq(val _id: String, val done: Boolean)
data class DoneTodoRes(val success: String)

//data class EditTodoReq()
//data class EditTodoRes(val success: String)

//data class DeleteTodoReq()
//data class DeleteTodoRes(val success: String)

