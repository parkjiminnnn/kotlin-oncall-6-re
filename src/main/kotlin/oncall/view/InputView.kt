package oncall.view

import camp.nextstep.edu.missionutils.Console

object InputView {
    fun inputMonthAndStartDay():String {
        println("비상 근무를 배정할 월과 시작 요일을 입력하세요> ")
        return Console.readLine()
    }

    fun inputWeekdayWorkers():String {
        println("평일 비상 근무 순번대로 사원 닉네임을 입력하세요>")
        return Console.readLine()
    }

    fun inputWeekendWorkers():String {
        println("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요>")
        return Console.readLine()
    }
}