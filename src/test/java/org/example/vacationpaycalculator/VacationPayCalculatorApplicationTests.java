package org.example.vacationpaycalculator;

import jakarta.validation.ValidationException;
import org.example.vacationpaycalculator.controller.VacationPayCalculatorController;
import org.example.vacationpaycalculator.service.days.DaysCalculationServiceImpl;
import org.example.vacationpaycalculator.service.vacation.VacationPayCalculateServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class VacationPayCalculatorApplicationTests {

    private final BigDecimal averageSalaryPerYear = new BigDecimal("60000");

    private final VacationPayCalculateServiceImpl serviceVacation = new VacationPayCalculateServiceImpl();

    private final DaysCalculationServiceImpl serviceDays = new DaysCalculationServiceImpl();


    @DisplayName("JUnit test for calculate method (positive scenario)")
    @Test
    void shouldReturnVacationPay_whenGivenOnlyDays() {
        int vacationDays = 10;

        BigDecimal givenSalary = serviceVacation
                .getVacationPayCalculation(averageSalaryPerYear, vacationDays)
                .getVacationPay();

        assertEquals("17815.80", givenSalary.toString());
    }

	@DisplayName("JUnit test for calculate method (negative scenario)")
	@Test
	void shouldThrowException_whenDaysNotMatchTheDates() {
		Integer vacationDays = 7;
		LocalDate startVacationDate = LocalDate.of(2024, 1, 1);
		LocalDate endVacationDate = LocalDate.of(2024, 1, 10);

		assertThrows(ValidationException.class,
				() -> serviceDays.calculateDays(startVacationDate, endVacationDate, vacationDays));
	}

    @DisplayName("JUnit test for calculate method (positive scenario)")
    @Test
    void shouldReturnBusinessDays_whenGivenDaysAndCorrectDates() {
        int vacationDays = 10;
        LocalDate startVacationDate = LocalDate.of(2024, 1, 1);
        LocalDate endVacationDate = LocalDate.of(2024, 1, 10);

        Integer businessDays = serviceDays.calculateDays(startVacationDate, endVacationDate, vacationDays);

        assertEquals("2", businessDays.toString());
    }

	@DisplayName("JUnit test for calculate method (negative scenario)")
	@Test
	void shouldThrowException_whenGivenCorrectDatesAndNegativeDays() {
		int vacationDays = -10;
		LocalDate startVacationDate = LocalDate.of(2024, 1, 1);
		LocalDate endVacationDate = LocalDate.of(2024, 1, 10);

        assertThrows(ValidationException.class,
                () -> serviceDays.calculateDays(startVacationDate, endVacationDate, vacationDays));
	}

	@DisplayName("JUnit test for calculate method (positive scenario)")
	@Test
	void shouldBusinessDays_whenGivenCorrectAddDataAnZeroDay() {
		int vacationDays = 0;
		LocalDate startVacationDate = LocalDate.of(2024, 1, 1);
		LocalDate endVacationDate = LocalDate.of(2024, 1, 10);

        Integer businessDays = serviceDays.calculateDays(startVacationDate, endVacationDate, vacationDays);

		assertEquals("2", businessDays.toString());
	}

	@DisplayName("JUnit test for calculate method (negative scenario)")
	@Test
	void shouldThrowException_whenGivenWrongDates() {
		int vacationDays = 10;
		LocalDate startVacationDate = LocalDate.of(2024, 1, 10);
		LocalDate endVacationDate = LocalDate.of(2024, 1, 1);

		assertThrows(ValidationException.class,
				() -> serviceDays.calculateDays(startVacationDate, endVacationDate, vacationDays));
	}

	@DisplayName("JUnit test for calculate method (positive scenario)")
	@Test
	void shouldBusinessDays_whenGivenPreviousYear() {
		int vacationDays = 22;
		LocalDate startVacationDate = LocalDate.of(2023, 12, 20);
		LocalDate endVacationDate = LocalDate.of(2024, 1, 10);

		Integer businessDays = serviceDays.calculateDays(startVacationDate, endVacationDate, vacationDays);

		assertEquals("10", businessDays.toString());
	}

//	@DisplayName("JUnit test for calculate method (negative scenario)")
//	@Test
//	void shouldThrowException_whenGivenDaysZeroAndNullEndDate() {
//		int vacationDays = 0;
//		LocalDate endVacationDate = LocalDate.of(2024, 1, 1);
//
//		assertThrows(ValidationException.class,
//				() -> serviceDays.calculateDays(null, endVacationDate, vacationDays));
//	}
//
//	@DisplayName("JUnit test for calculate method (negative scenario)")
//	@Test
//	void shouldThrowException_whenGivenDaysZeroAndNullFrom() {
//		int days = 0;
//		LocalDate to = LocalDate.of(2023, 1, 14);
//
//		assertThrows(ValidationException.class,
//				() -> service.calculate(salary, days, null, to));
//	}


}
