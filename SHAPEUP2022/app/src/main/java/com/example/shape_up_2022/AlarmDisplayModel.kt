package lilcode.aop.p3.c03.alarm

import kotlin.random.Random

data class AlarmDisplayModel(
    val hour: Int, // 0~23
    val minute: Int,
    var onOff: Boolean
) {

    fun makeDataForDB(): String {
        return "$hour:$minute"
    }

    // 시간 분 랜덤 맞추기
    /*
    *Random().nextInt(12)
    * (0..10).random()
    * */

    // 형식에 맞게 시:분 가져오기.
    val timeText: String
        get() {
            //val h = "%02d".format(if (hour < 12) hour else hour - 12)
            val h= (0..12).random()
            //val m = "%02d".format(minute)
            val m =(0..60).random()



            return "$h:$m"
        }

    // am pm 가져오기.
    val ampmText: String
        get() {
            return if (hour < 12) "AM" else "PM"
        }

    val onOffText: String
    get(){
        return if(onOff) "알람 끄기" else "알람 켜기"
    }
}