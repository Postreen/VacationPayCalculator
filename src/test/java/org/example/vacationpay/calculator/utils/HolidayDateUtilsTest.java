package org.example.vacationpay.calculator.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HolidayDateUtilsTest {

    @DisplayName("Определение третьего понедельника января")
    @Test
    void testGetNthWeekdayOfMonth() {
        assertEquals(LocalDate.of(2025, 1, 20),
                HolidayDateUtils.getNthWeekdayOfMonth(2025, Month.JANUARY, DayOfWeek.MONDAY, 3));
    }

    @DisplayName("Определение последнего понедельника мая")
    @Test
    void testGetLastWeekdayOfMonth() {
        assertEquals(LocalDate.of(2025, 5, 26),
                HolidayDateUtils.getLastWeekdayOfMonth(2025, Month.MAY, DayOfWeek.MONDAY));
    }
}
