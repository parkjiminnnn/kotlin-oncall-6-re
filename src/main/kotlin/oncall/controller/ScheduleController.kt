package oncall.controller

import oncall.domain.Calendar
import oncall.domain.Schedule
import oncall.domain.Workers
import oncall.view.InputView.inputMonthAndStartDay
import oncall.view.InputView.inputWeekdayWorkers
import oncall.view.InputView.inputWeekendWorkers
import oncall.view.OutputView.printSchedule

class ScheduleController {
    fun run() {
        val rawMonthAndStartDay = inputMonthAndStartDay()
        val rawWeekdayWorkers = inputWeekdayWorkers()
        val rawHolidayWorkers = inputWeekendWorkers()

        val calendar = Calendar(rawMonthAndStartDay)
        val workers = Workers(rawWeekdayWorkers, rawHolidayWorkers)
        val schedule = Schedule(calendar, workers)

        printSchedule(calendar.month, calendar.startDay,calendar.getEndDate(),schedule.inputTotalWorkers())
    }
}