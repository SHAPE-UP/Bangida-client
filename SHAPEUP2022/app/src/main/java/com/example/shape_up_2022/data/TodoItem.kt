package com.example.shape_up_2022.data

// DB의 모델 중 Todo에 해당
data class TodoItem (
                val todowork:String="할일",
                val todorole:Todorole?=null,
                val todotime:Int=17,
                val todoref:Int=0,
                val date:String="2022-09-01",
                val done:Boolean=false
)

data class Todorole (val name: String)

// todorole을 왜 굳이 한겹 싸인 형태로 더럽게 만든 건지가 의아할 수 있지만...
// 먼 미래에 배열로 바뀔 수도 있는 필드니까 괜찮지 않을까요
// 백엔드에서 주는 res 대신 프론트쪽 저장형태를 고쳤습니다