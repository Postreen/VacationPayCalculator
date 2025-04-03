package org.example.vacationpay.calculator.service.days.businessday.holiday;

import org.example.vacationpay.calculator.dto.enums.Region;

import java.time.LocalDate;
import java.util.List;

public interface HolidayProviderService {
    List<LocalDate> getHolidays(Region region, LocalDate startDate);
}
