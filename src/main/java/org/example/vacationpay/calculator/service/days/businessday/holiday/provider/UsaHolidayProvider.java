package org.example.vacationpay.calculator.service.days.businessday.holiday.provider;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.utils.HolidayDateUtils;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component("USA")
public class UsaHolidayProvider implements CountryHolidayProvider {

    @Override
    public List<LocalDate> getHolidays(int year) {
        return List.of(
                LocalDate.of(year, 1, 1),  // Новый год
                HolidayDateUtils.getNthWeekdayOfMonth(year, Month.JANUARY, DayOfWeek.MONDAY, 3), // День Мартина Лютера Кинга
                HolidayDateUtils.getNthWeekdayOfMonth(year, Month.FEBRUARY, DayOfWeek.MONDAY, 3), // Президентский день
                HolidayDateUtils.getLastWeekdayOfMonth(year, Month.MAY, DayOfWeek.MONDAY), // День памяти
                LocalDate.of(year, 6, 19),  // День эмансипации
                LocalDate.of(year, 7, 4),   // День независимости
                LocalDate.of(year, 11, 11), // День ветеранов
                LocalDate.of(year, 12, 25) // Рождество
        );
    }

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.USA;
    }
}
