package oncall.domain

class Schedule(private val calendar: Calendar, workers: Workers) {
    private val holidayWorkers = workers.holidayWorkers
    private val weekdayWorkers = workers.weekdayWorkers

    private fun weekdayWorkersToDate(): List<Pair<String, Int>> {
        var index = 0
        return calendar.getWeekdays().map {
            val worker = weekdayWorkers[index]
            index = (index + 1) % weekdayWorkers.size
            Pair(worker, it)
        }
    }

    private fun holidayWorkersToDate(): List<Pair<String, Int>> {
        var index = 0

        return calendar.getTotalHolidays().map {
            val worker = holidayWorkers[index]
            index = (index + 1) % holidayWorkers.size
            Pair(worker, it)
        }
    }

    fun inputTotalWorkers(): List<String> {
        val endDate = calendar.getEndDate()
        val schedule = MutableList(endDate) { "" }
        val holidayWorkersToDate = holidayWorkersToDate()
        val weekdayWorkersToDate = weekdayWorkersToDate()

        schedule.forEachIndexed { index, _ ->
            val currentWorker = holidayWorkersToDate.find { it.second == index }?.first
                ?: weekdayWorkersToDate.find { it.second == index }?.first.toString()
            schedule.set(index, currentWorker)
            if (index > 0 && schedule[index - 1] == schedule[index]) {
                var currentIndex = holidayWorkersToDate.indexOfFirst { it.second == index } + 1
                if (currentIndex > holidayWorkersToDate.size) currentIndex = 0
                if (holidayWorkersToDate.find { it.second == index }?.first != null) {
                    schedule.set(index, holidayWorkersToDate[currentIndex].first)
                } else {
                    var currentIndex = weekdayWorkersToDate.indexOfFirst { it.second == index } + 1
                    if (currentIndex > weekdayWorkersToDate().size) currentIndex = 0
                    schedule.set(index, weekdayWorkersToDate[currentIndex].first)
                }
            }
        }
        return schedule
    }
}


