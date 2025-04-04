package org.example.vacationpay.calculator.service.days.businessday.holiday.provider;

import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static org.example.vacationpay.calculator.utils.HolidayDateUtils.getRuHolidays;

@Component
public class RussiaHolidayProvider implements CountryHolidayProvider {

    @Override
    public List<LocalDate> getHolidays(int year) {
        return getRuHolidays(year);
    }

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.RU;
    }
}
