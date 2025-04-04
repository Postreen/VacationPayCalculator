package org.example.vacationpay.calculator.service.days.businessday.holiday;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.service.days.businessday.holiday.provider.CountryHolidayProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HolidayProviderServiceImpl implements HolidayProviderService {

    private final List<CountryHolidayProvider> holidayProviders;

    @Override
    public List<LocalDate> getHolidays(Region region, LocalDate startDate) {

        List<LocalDate> holidays = holidayProviders.stream()
                .filter(provider -> provider.matchesRegion(region))
                .findFirst()
                .map(provider -> provider.getHolidays(startDate.getYear()))
                .orElse(List.of());

        return holidays;
    }
}
