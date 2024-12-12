package oncall.utils

object ErrorHandler {

    fun handleMonth(rawMonthAndStartDate: String){
        val rawMonth = rawMonthAndStartDate.split(',')[0]
        val month = rawMonth.toIntOrNull() ?: throw IllegalArgumentException("[ERROR] 숫자를 입력해주세요.")
        if (month !in 1..12) throw IllegalArgumentException("[ERROR] 1-12사이 숫자를 입력해주세요")
    }
}