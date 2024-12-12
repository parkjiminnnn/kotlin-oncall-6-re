package oncall.controller

import oncall.domain.Calendar
import oncall.domain.Schedule
import oncall.domain.Workers
import oncall.utils.ErrorHandler.handleMonth
import oncall.view.InputView.inputMonthAndStartDay
import oncall.view.InputView.inputWeekdayWorkers
import oncall.view.InputView.inputWeekendWorkers
import oncall.view.OutputView.printSchedule

class ScheduleController {
    fun run() {
        val rawMonthAndStartDay = validMonth()
        val rawWeekdayWorkers = inputWeekdayWorkers()
        val rawHolidayWorkers = inputWeekendWorkers()

        val calendar = Calendar(rawMonthAndStartDay)
        val workers = Workers(rawWeekdayWorkers, rawHolidayWorkers)
        val schedule = Schedule(calendar, workers)

        printSchedule(calendar.month, calendar.startDay,calendar.getEndDate(),schedule.inputTotalWorkers())
    }

    private fun validMonth():String {
        while (true) {
            try {
                val rawMonthAndStartDay = inputMonthAndStartDay()
                handleMonth(rawMonthAndStartDay)
                return  rawMonthAndStartDay
            }catch (e:IllegalArgumentException) {
                println(e)
            }
        }
    }
}