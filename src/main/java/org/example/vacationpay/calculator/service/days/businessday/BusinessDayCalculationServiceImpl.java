package org.example.vacationpay.calculator.service.days.businessday;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.service.days.businessday.holiday.HolidayProviderService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessDayCalculationServiceImpl implements BusinessDayCalculationService {

    private final HolidayProviderService holidayProviderService;

    @Override
    public int calculate(LocalDate startDate, LocalDate endDate, Region region) {
        Set<LocalDate> holidays = new HashSet<>(holidayProviderService.getHolidays(region, startDate));

        int businessDays = (int) Stream.iterate(startDate, date -> date.isBefore(endDate.plusDays(1)), date -> date.plusDays(1))
                .filter(date -> !(date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.SUNDAY
                        || holidays.contains(date)))
                .count();

        log.info("Количество рабочих дней: {}", businessDays);

        return businessDays;
    }
}
