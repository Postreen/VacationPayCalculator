package org.example.vacationpay.calculator.service.tax.provider;

import org.example.vacationpay.calculator.dto.enums.Region;

import java.math.BigDecimal;

public interface RegionTaxProvider {

    boolean matchesRegion(Region region);

    BigDecimal getTaxRate(BigDecimal monthlySalary);
}
