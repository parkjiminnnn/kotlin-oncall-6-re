package oncall.domain

class Workers(rawWeekdayWorkers: String,rawHolidayWorkers:String) {
    val weekdayWorkers = rawWeekdayWorkers.split(',')
    val holidayWorkers = rawHolidayWorkers.split(',')
}