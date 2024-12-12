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
        var schedule = MutableList(endDate) { "" }
        val holidayWorkersToDate = holidayWorkersToDate()
        val weekdayWorkersToDate = weekdayWorkersToDate()

        schedule.forEachIndexed { index, _ ->
            schedule[index] = findCurrentWorker(index, holidayWorkersToDate, weekdayWorkersToDate)
            if (index > 0 && schedule[index - 1] == schedule[index]) {
                schedule = refreshSchedule(schedule, index, holidayWorkersToDate, weekdayWorkersToDate)
            }
        }
        return schedule
    }

    private fun findCurrentWorker(
        index: Int,
        holidayWorkersToDate: MutableList<Pair<String, Int>>,
        weekdayWorkersToDate: MutableList<Pair<String, Int>>
    ): String {
        return holidayWorkersToDate.find { it.second == index + 1 }?.first
            ?: weekdayWorkersToDate.find { it.second == index + 1 }?.first.toString()
    }

    private fun refreshSchedule(
        schedule: MutableList<String>,
        index: Int,
        holidayWorkersToDate: MutableList<Pair<String, Int>>,
        weekdayWorkersToDate: MutableList<Pair<String, Int>>
    ): MutableList<String> {
        val currentHolidayIndex = holidayWorkersToDate.indexOfFirst { it.second == index + 1 }
        if (currentHolidayIndex != -1) {
            holidayWorkersToDate.changeToWorker(currentHolidayIndex)
            schedule[index] = holidayWorkersToDate[currentHolidayIndex].first
        } else changeToWeekdayWorkers(index, weekdayWorkersToDate, schedule)
        return schedule
    }

    private fun changeToWeekdayWorkers(
        index: Int,
        weekdayWorkersToDate: MutableList<Pair<String, Int>>,
        schedule: MutableList<String>
    ) {
        val currentWeekdayIndex = weekdayWorkersToDate.indexOfFirst { it.second == index + 1 }
        if (currentWeekdayIndex != -1) {
            weekdayWorkersToDate.changeToWorker(currentWeekdayIndex)
            schedule[index] = weekdayWorkersToDate[currentWeekdayIndex].first
        }
    }

    private fun MutableList<Pair<String, Int>>.changeToWorker(index: Int) {
        val nextIndex = (index + 1) % this.size
        val temp = this[index]
        this[index] = this[nextIndex]
        this[nextIndex] = temp
    }
}



