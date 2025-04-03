package org.example.vacationpay.calculator.service.days.businessday.holiday.provider;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static org.example.vacationpay.calculator.utils.HolidayDateUtils.getEuHolidays;

@Component
public class EuropeHolidayProvider implements CountryHolidayProvider {

    @Override
    public List<LocalDate> getHolidays(int year) {
        return getEuHolidays(year);
    }

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.EU;
    }
}