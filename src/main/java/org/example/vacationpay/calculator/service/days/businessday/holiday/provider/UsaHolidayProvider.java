package org.example.vacationpay.calculator.service.days.businessday.holiday.provider;

import lombok.RequiredArgsConstructor;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static org.example.vacationpay.calculator.utils.HolidayDateUtils.getUsaHolidays;

@Component
@RequiredArgsConstructor
public class UsaHolidayProvider implements CountryHolidayProvider {

    @Override
    public List<LocalDate> getHolidays(int year) {
        return getUsaHolidays(year);
    }

    @Override
    public boolean matchesRegion(Region region) {
        return region == Region.USA;
    }
}
