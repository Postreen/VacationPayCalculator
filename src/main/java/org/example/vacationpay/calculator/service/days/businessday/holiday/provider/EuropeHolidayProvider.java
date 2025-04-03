package org.example.vacationpay.calculator.service.days.businessday.holiday.provider;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EuropeHolidayProvider implements CountryHolidayProvider {

    @Override
    public List<LocalDate> getHolidays(int year) {
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

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.EU;
    }
}