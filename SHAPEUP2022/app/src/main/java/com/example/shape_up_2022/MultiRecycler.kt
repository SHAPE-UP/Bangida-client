package com.example.shape_up_2022

// 리사이클러 뷰의 값을 저장
const val todo_type = 1
const val list_type = 2

data class MainListViewData( // 메인 목록에서 사용
    val title: String?,
)

data class MainTodoListViewData(
    val image: Int?,
    val todo: String?,
    val type: Int?,
)
