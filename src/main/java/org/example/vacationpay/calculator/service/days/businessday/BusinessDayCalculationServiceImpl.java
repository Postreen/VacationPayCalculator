package org.example.vacationpay.calculator.service.days.businessday;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class BusinessDayCalculationServiceImpl implements BusinessDayCalculationService {

    @Override
    public int calculate(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays) {
        return (int) Stream.iterate(startDate, date -> !date.isAfter(endDate), date -> date.plusDays(1))
                .filter(date -> !(date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.SUNDAY
                        || holidays.contains(date)))
                .count();
    }
}
