package oncall.domain

class Workers(rawWorkers: String) {
    val weekdayWorkers = rawWorkers.split(',')
    val holidayWorkers = rawWorkers.split(',')
}