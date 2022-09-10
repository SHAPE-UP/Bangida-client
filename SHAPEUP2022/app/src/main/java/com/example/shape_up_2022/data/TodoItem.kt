package com.example.shape_up_2022.data

// DB의 모델 중 Todo와 일치함
class TodoItem (
                val todowork:String="할일",
                val todorole:Todorole?=null,
                val todotime:String="시간 설정",
                val todoref:String="관련 활동",
                val date:String="2022-09-01",
                val done:Boolean=false
)

class Todorole (val name: String)

// todorole을 왜 굳이 한겹 싸인 형태로 더럽게 만든 건지가 의아할 수 있지만...
// 먼 미래에 배열로 바뀔 수도 있는 필드니까 괜찮지 않을까요
// 백엔드에서 주는 res 대신 프론트쪽 저장형태를 고쳤습니다