package oncall.domain

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CalendarTest {
    private val calendar = Calendar("5,월")

    @Test
    fun `며칠까지 있는지 테스트`() {
        val excepted = 31
        val result = calendar.getEndDate()

        assertEquals(excepted, result)
    }

    @Test
    fun `휴일 테스트`() {
        val excepted = listOf(6, 7, 13, 14, 20, 21, 27, 28, 5)
        val result = calendar.getTotalHolidays()

        assertEquals(excepted, result)
    }

    @Test
    fun `평일 테스트`() {
        val excepted = listOf(1,2,3,4,8,9,10,11,12,15,16,17,18,19,22,23,24,25,26,29,30,31)
        val result = calendar.getWeekdays()

        assertEquals(excepted, result)
    }

}