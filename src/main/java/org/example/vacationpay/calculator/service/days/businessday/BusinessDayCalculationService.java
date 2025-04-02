package org.example.vacationpay.calculator.service.days.businessday;

import java.time.LocalDate;
import java.util.List;

public interface BusinessDayCalculationService {
    int calculate(LocalDate startDate, LocalDate endDate, List<LocalDate> holidays);
}
