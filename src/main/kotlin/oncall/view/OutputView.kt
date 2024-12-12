package oncall.view

import oncall.utils.SevenDays

object OutputView {
    fun printSchedule(month: Int, startDay: String, endDate: Int, totalWorkers: List<String>) {
        var index = SevenDays.entries.indexOfFirst { it.dayOfWeek == startDay }
        for (date in 1..endDate) {
            val dayOfWeek = SevenDays.entries[index].dayOfWeek
            println("${month}월 ${date}일 $dayOfWeek ${totalWorkers[date - 1]}")
            index = (index + 1) % SevenDays.entries.size
        }
    }
}