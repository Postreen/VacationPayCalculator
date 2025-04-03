package org.example.vacationpay.calculator.service.tax;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.service.tax.provider.RegionTaxProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxCalculationServiceImpl implements TaxCalculationService {

    private final List<RegionTaxProvider> taxProviders;

    @Override
    public BigDecimal calculateNdfl(Region region, BigDecimal monthlySalary) {
        System.out.println(taxProviders);

        BigDecimal ndfl = taxProviders.stream()
                .filter(provider -> provider.matchesRegion(region))
                .findFirst()
                .map(provider -> provider.getTaxRate(monthlySalary))
                .orElseThrow(() -> new IllegalArgumentException("Не найден налоговый провайдер для региона: " + region));

        log.info("Налог для региона {} по сумме {} равен: {}", region, monthlySalary, ndfl);

        return ndfl;
    }
}
