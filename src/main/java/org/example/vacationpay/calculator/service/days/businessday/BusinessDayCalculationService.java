package org.example.vacationpay.calculator.service.days.businessday;

import org.example.vacationpay.calculator.dto.enums.Region;

import java.time.LocalDate;
import java.util.List;

public interface BusinessDayCalculationService {
    int calculate(LocalDate startDate, LocalDate endDate, Region region);
}
