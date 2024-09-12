package org.example.vacationpaycalculator.service.days;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DaysCalculationServiceImpl implements DaysCalculationService {
    public final static int CURRENT_YEAR = LocalDate.now().getYear();

    @Override
    public int calculateDays(LocalDate startVacationDate, LocalDate endVacationDate, int vacationDays) {

        checkDate(startVacationDate, endVacationDate, vacationDays);

        if (startVacationDate != null && endVacationDate != null) {
            vacationDays = (int) ChronoUnit.DAYS.between(startVacationDate, endVacationDate) + 1;
        }
        return calculateBusinessDays(vacationDays, startVacationDate);
    }

    @Override
    public int calculateBusinessDays(int vacationDays, LocalDate startVacationDate) {
        Predicate<LocalDate> holidays = getHolidays()::contains;

        List<LocalDate> listBusinessDays = Stream
                .iterate(startVacationDate, nextVacationDate -> nextVacationDate
                        .plusDays(1))
                .limit(vacationDays)
                .filter(vacationDate -> !(holidays.test(vacationDate)))
                .filter(vacationDate -> !(vacationDate.getDayOfWeek() == DayOfWeek.SATURDAY || vacationDate.getDayOfWeek() == DayOfWeek.SUNDAY))
                .collect(Collectors.toList());

        return listBusinessDays.size();
    }

    public static List<LocalDate> getHolidays() {
        List<LocalDate> holidays = Stream.of(
                LocalDate.of(CURRENT_YEAR, 1, 1),
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
            throw new ValidationException("Введены некорректные даты отпуска");
        }
        if (vacationDays != 0 && vacationDays != ((int) ChronoUnit.DAYS.between(startVacationDate, endVacationDate) + 1)) {
            throw new ValidationException("Указанное количество дней отпуска не совпадает "
                    + "с количеством дней отпуска по датам");
        }
    }

}
