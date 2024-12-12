package oncall.domain

import oncall.utils.SevenDays

class Calendar(rawMonthAndStartDay: String) {
    private val monthAndStartDay = rawMonthAndStartDay.split(',')
    val month = monthAndStartDay[0].toInt()
    val startDay = monthAndStartDay[1]

    fun getEndDate(): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            else -> 28
        }
    }

    fun getLegalHolidays(): List<Int> {
        return when (month) {
            1, 3 -> listOf(1)
            5 -> listOf(5)
            6 -> listOf(6)
            8 -> listOf(15)
            10 -> listOf(3, 9)
            12 -> listOf(25)
            else -> listOf()
        }
    }

    fun getWeekends(): MutableList<Int> {
        val dayOfWeek = SevenDays.entries.indexOfFirst { it.dayOfWeek == startDay } + 1
        val holidays = mutableListOf<Int>()
        var saturday = SevenDays.entries.size - dayOfWeek
        var sunday = saturday + 1
        while (saturday <= getEndDate()) {
            if (saturday > 0) holidays.add(saturday)
            if (sunday <= getEndDate()) holidays.add(sunday)
            saturday += 7
            sunday += 7
        }
        return holidays
    }

    fun getTotalHolidays(): List<Int> {
        val weekends = getWeekends()
        val legalHolidays = getLegalHolidays()
        return weekends.apply { legalHolidays.forEach { add(it) } }.toSet().toList()
    }

    fun getWeekdays(): List<Int> {
        val holidays = getWeekends()
        val allDays = MutableList(getEndDate()) { it }
        holidays.forEach { allDays.remove(it) }
        allDays.remove(0)
        return allDays
    }
}