package org.example.vacationpay.calculator.service.days;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vacationpay.calculator.dto.enums.Region;
import org.example.vacationpay.calculator.exception.VacationValidationException;
import org.example.vacationpay.calculator.service.days.businessday.BusinessDayCalculationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class DaysCalculationServiceImpl implements DaysCalculationService {

    private final BusinessDayCalculationService businessDayCalculationService;

    @Override
    public int calculateDays(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays, Region region) {

        if (startVacationDate == null && endVacationDate == null) {
            throw new VacationValidationException("Необходимо указать либо даты отпуска, либо количество дней.");
        }

        if (startVacationDate == null) {
            startVacationDate = calculateStartVacationDate(endVacationDate, vacationDays);
            log.info("Расчетная начальная дата отпуска: {}", startVacationDate);
        } else if (endVacationDate == null) {
            endVacationDate = calculateEndVacationDate(startVacationDate, vacationDays);
            log.info("Расчетная конечная дата отпуска: {}", endVacationDate);
        }
        checkDate(startVacationDate, endVacationDate, vacationDays);

        return businessDayCalculationService.calculate(startVacationDate, endVacationDate, region);
    }

    private LocalDate calculateStartVacationDate(LocalDate endVacationDate, int vacationDays) {
        return endVacationDate.minusDays(vacationDays);
    }

    private LocalDate calculateEndVacationDate(LocalDate startVacationDate, int vacationDays) {
        return startVacationDate.plusDays(vacationDays);
    }

    private void checkDate(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays) {
        if (startVacationDate.isAfter(endVacationDate)) {
            throw new VacationValidationException("Дата начала отпуска не может быть позже даты окончания.");
        }

        long calculatedDays = ChronoUnit.DAYS.between(startVacationDate, endVacationDate);

        if (vacationDays != calculatedDays) {
            throw new VacationValidationException(String.format(
                    "Указанное количество дней (%d) не совпадает с количеством дней по датам (%d).",
                    vacationDays, calculatedDays));
        }
    }
}