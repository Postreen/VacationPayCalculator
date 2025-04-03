package org.example.vacationpay.calculator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "vacation")
public class VacationProperties {
    private BigDecimal averageDaysInMonth;
    private BigDecimal ndfl;
}
