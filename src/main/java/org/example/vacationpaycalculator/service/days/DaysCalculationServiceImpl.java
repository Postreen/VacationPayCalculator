package org.example.vacationpaycalculator.service.days;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DaysCalculationServiceImpl implements DaysCalculationService {
    public final static int CURRENT_YEAR = LocalDate.now().getYear();
    private static final Logger logger = LoggerFactory.getLogger("org.example.vacationpaycalculator.service");

    @Override
    public int calculateDays(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays) {

        if (startVacationDate == null) {
            startVacationDate = endVacationDate.minusDays(vacationDays - 1);
            logger.info("Расчетная начальная дата отпуска = {} дней", startVacationDate);
        } else if (endVacationDate == null) {
            endVacationDate = startVacationDate.plusDays(vacationDays - 1);
            logger.info("Расчетная конечная дата отпуска = {} дней", endVacationDate);
        }

        checkDate(startVacationDate, endVacationDate, vacationDays);
        vacationDays = (int) ChronoUnit.DAYS.between(startVacationDate, endVacationDate) + 1;
        logger.info("Количество дней отпуска = {} дней", vacationDays);

        return calculateBusinessDays(vacationDays, startVacationDate);
    }

    public int calculateBusinessDays(int vacationDays, LocalDate startVacationDate) {
        List<LocalDate> holidays = getHolidays();

        int businessDaysCount = 0;

        LocalDate endVacationDate = startVacationDate.plusDays(vacationDays - 1);
        for (LocalDate date = startVacationDate; !date.isAfter(endVacationDate); date = date.plusDays(1)) {
            if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                    && !holidays.contains(date)) {
                businessDaysCount++;
            }
        }

        return businessDaysCount;
    }

    public static List<LocalDate> getHolidays() {
        List<LocalDate> holidays = Stream.of(
                LocalDate.of(CURRENT_YEAR, 1, 1), // Новогодние каникулы
                LocalDate.of(CURRENT_YEAR, 1, 2), // Новогодние каникулы
                LocalDate.of(CURRENT_YEAR, 1, 3), // Новогодние каникулы
                LocalDate.of(CURRENT_YEAR, 1, 4), // Новогодние каникулы
                LocalDate.of(CURRENT_YEAR, 1, 5), // Новогодние каникулы
                LocalDate.of(CURRENT_YEAR, 1, 6), // Новогодние каникулы
                LocalDate.of(CURRENT_YEAR, 1, 7), // Рождество
                LocalDate.of(CURRENT_YEAR, 1, 8), // Новогодние каникулы
                LocalDate.of(CURRENT_YEAR, 2, 23),// День защитника отечества
                LocalDate.of(CURRENT_YEAR, 3, 8), // Международный женский день
                LocalDate.of(CURRENT_YEAR, 5, 1), // Праздник весны и труда
                LocalDate.of(CURRENT_YEAR, 5, 9), // День победы
                LocalDate.of(CURRENT_YEAR, 6, 12),// День России
                LocalDate.of(CURRENT_YEAR, 11, 4) // День народного единства
        ).collect(Collectors.toList());

        return Collections.unmodifiableList(holidays);
    }

    public static void checkDate(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays) {

        if (startVacationDate.isAfter(endVacationDate)) {
            throw new ValidationException("Дата начала отпуска не может быть позже даты окончания отпуска.");
        }
        if (startVacationDate.equals(endVacationDate)) {
            throw new ValidationException("Должно быть известно количество дней отпуска или даты отпуска");
        }
        long calculatedDays = ChronoUnit.DAYS.between(startVacationDate, endVacationDate) + 1;
        if (vacationDays != 0 && vacationDays != calculatedDays) {
            throw new ValidationException(String.format("Указанное количество дней отпуска (%d) " +
                    "не совпадает с количеством дней по датам (%d).", vacationDays, calculatedDays));
        }
    }
}
