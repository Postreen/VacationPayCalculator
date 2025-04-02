package org.example.vacationpay.calculator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "vacation")
public class VacationProperties {
    private BigDecimal averageDaysInMonth;
    private BigDecimal ndfl;
    private List<String> holidays;

    public List<LocalDate> getHolidaysForCurrentYear() {
        int currentYear = LocalDate.now().getYear();
        return holidays.stream()
                .map(date -> LocalDate.parse(currentYear + "-" + date, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .toList();
    }
}
