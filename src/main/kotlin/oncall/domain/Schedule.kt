package oncall.domain

class Schedule(private val calendar: Calendar, workers: Workers) {
    private val holidayWorkers = workers.holidayWorkers
    private val weekdayWorkers = workers.weekdayWorkers

    private fun weekdayWorkersToDate(): MutableList<Pair<String, Int>> {
        var index = 0
        return calendar.getWeekdays().map {
            val worker = weekdayWorkers[index]
            index = (index + 1) % weekdayWorkers.size
            Pair(worker, it)
        }.toMutableList()
    }

    private fun holidayWorkersToDate(): MutableList<Pair<String, Int>> {
        var index = 0

        return calendar.getTotalHolidays().map {
            val worker = holidayWorkers[index]
            index = (index + 1) % holidayWorkers.size
            Pair(worker, it)
        }.toMutableList()
    }

    fun inputTotalWorkers(): List<String> {
        val endDate = calendar.getEndDate()
        val schedule = MutableList(endDate) { "" }
        val holidayWorkersToDate = holidayWorkersToDate()
        val weekdayWorkersToDate = weekdayWorkersToDate()

        schedule.forEachIndexed { index, _ ->
            val currentWorker = holidayWorkersToDate.find { it.second == index + 1 }?.first
                ?: weekdayWorkersToDate.find { it.second == index + 1 }?.first.toString()
            schedule.set(index, currentWorker)
            if (index > 0 && schedule[index - 1] == schedule[index]) {
                var currentHolidayIndex = holidayWorkersToDate.indexOfFirst { it.second - 1 == index } + 1
                if (currentHolidayIndex > holidayWorkersToDate.size) currentHolidayIndex = 0
                if (holidayWorkersToDate.find { it.second - 1 == index }?.first != null) {
                    schedule.set(index, holidayWorkersToDate[currentHolidayIndex].first)
                    holidayWorkersToDate[currentHolidayIndex] = holidayWorkersToDate[currentHolidayIndex + 1]
                    holidayWorkersToDate[currentHolidayIndex + 1] = holidayWorkersToDate[currentHolidayIndex]
                } else {
                    var currentWeekdayIndex = weekdayWorkersToDate.indexOfFirst { it.second - 1 == index } + 1
                    if (currentWeekdayIndex > weekdayWorkersToDate().size) currentWeekdayIndex = 0
                    schedule.set(index, weekdayWorkersToDate[currentWeekdayIndex].first)
                    weekdayWorkersToDate[currentWeekdayIndex] = weekdayWorkersToDate[currentWeekdayIndex + 1]
                    weekdayWorkersToDate[currentWeekdayIndex + 1] = weekdayWorkersToDate[currentWeekdayIndex]
                }
            }
            schedule.set(index, currentWorker)
        }
        return schedule
    }
}


