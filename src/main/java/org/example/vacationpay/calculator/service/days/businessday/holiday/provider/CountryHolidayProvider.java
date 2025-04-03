package org.example.vacationpay.calculator.service.days.businessday.holiday.provider;

import java.time.LocalDate;
import java.util.List;
import org.example.vacationpay.calculator.dto.enums.Region;

public interface CountryHolidayProvider {
    List<LocalDate> getHolidays(int year);

    boolean matchesRegion(Region region);
}
