package org.example.vacationpay.calculator.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public final class HolidayDateUtils {
    private HolidayDateUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Возвращает дату **N-го вхождения** указанного дня недели в данном месяце.
     * Например, третий понедельник января 2025 года:
     * `getNthWeekdayOfMonth(2025, Month.JANUARY, DayOfWeek.MONDAY, 3)`
     */
    public static LocalDate getNthWeekdayOfMonth(int year, Month month, DayOfWeek dayOfWeek, int weekNumber) {
        return LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(weekNumber, dayOfWeek));
    }

    /**
     * Возвращает дату **последнего вхождения** указанного дня недели в данном месяце.
     * Например, последний понедельник мая 2025 года:
     * `getLastWeekdayOfMonth(2025, Month.MAY, DayOfWeek.MONDAY)`
     */
    public static LocalDate getLastWeekdayOfMonth(int year, Month month, DayOfWeek dayOfWeek) {
        return LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.lastInMonth(dayOfWeek));
    }
}
