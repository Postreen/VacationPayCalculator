package org.example.vacationpay.calculator.service.days.businessday.holiday.provider;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component("RU")
public class RussiaHolidayProvider implements CountryHolidayProvider {

    @Override
    public List<LocalDate> getHolidays(int year) {
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

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.RU;
    }
}
