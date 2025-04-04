package org.example.vacationpay.calculator.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public final class HolidayDateUtils {
    private HolidayDateUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static List<LocalDate> getUsaHolidays(int year) {
        return List.of(
                LocalDate.of(year, 1, 1),  // Новый год
                getNthWeekdayOfMonth(year, Month.JANUARY, DayOfWeek.MONDAY, 3), // День Мартина Лютера Кинга
                getNthWeekdayOfMonth(year, Month.FEBRUARY, DayOfWeek.MONDAY, 3), // Президентский день
                getLastWeekdayOfMonth(year, Month.MAY, DayOfWeek.MONDAY), // День памяти
                LocalDate.of(year, 6, 19),  // День эмансипации
                LocalDate.of(year, 7, 4),   // День независимости
                LocalDate.of(year, 11, 11), // День ветеранов
                LocalDate.of(year, 12, 25)  // Рождество
        );
    }

    public static List<LocalDate> getRuHolidays(int year) {
        return List.of(
                LocalDate.of(year, 1, 1),  // Новый год
                LocalDate.of(year, 1, 2),  // Новый год
                LocalDate.of(year, 1, 3),  // Новый год
                LocalDate.of(year, 1, 4),  // Новый год
                LocalDate.of(year, 1, 5),  // Новый год
                LocalDate.of(year, 1, 6),  // Новый год
                LocalDate.of(year, 1, 7),  // Рождество
                LocalDate.of(year, 1, 8),  // Новый год
                LocalDate.of(year, 2, 23), // День защитника Отечества
                LocalDate.of(year, 3, 8),  // Международный женский день
                LocalDate.of(year, 5, 1),  // День труда
                LocalDate.of(year, 5, 9),  // День Победы
                LocalDate.of(year, 6, 12), // День России
                LocalDate.of(year, 11, 4)  // День народного единства
        );
    }

    public static List<LocalDate> getEuHolidays(int year) {
        return List.of(
                LocalDate.of(year, 1, 1),  // Новый год
                LocalDate.of(year, 5, 1),  // День труда
                LocalDate.of(year, 5, 8),  // День победы (во Франции, Чехии и др.)
                LocalDate.of(year, 8, 15), // Успение Богородицы (католические страны)
                LocalDate.of(year, 11, 1), // День всех святых
                LocalDate.of(year, 11, 11), // День памяти (Перемирие 1918 года)
                LocalDate.of(year, 12, 25), // Рождество
                LocalDate.of(year, 12, 26) // День Святого Стефана
        );
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
