package org.example.vacationpay.calculator.service.tax;

import org.example.vacationpay.calculator.dto.enums.Region;

import java.math.BigDecimal;

public interface TaxCalculationService {
    BigDecimal calculateNdfl(Region region, BigDecimal monthlySalary);
}
