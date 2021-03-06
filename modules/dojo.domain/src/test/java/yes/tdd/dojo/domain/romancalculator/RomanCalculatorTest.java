package yes.tdd.dojo.domain.romancalculator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static yes.tdd.dojo.domain.romancalculator.RomanNumber.of;

public class RomanCalculatorTest {
    @Nested
    class IntegrationTest {
        @Test
        void should_be_able_to_add_XIV_and_LX_to_LXXIV() {
            assertEquals(of("XIV").add(of("LX")).number(), "LXXIV");
        }
    }

    @Nested
    class UnitTest {
        @ParameterizedTest
        @CsvSource({
            // Common add.
            "'M',   'M',    'MM'",
            "'D',   'N',    'D'",
            "'C',   'C',    'CC'",
            "'CC',  'CC',   'CD'",
            "'L',   'N',    'L'",
            "'X',   'X',    'XX'",
            "'XX',  'XX',   'XL'",
            "'V',   'N',    'V'",
            "'I',   'I',    'II'",
            "'II',  'II',   'IV'",
            "'N',   'N',    'N'",
            // Cross unit add.
            "'L',   'XL',   'XC'",
            "'L',   'XLIX', 'XCIX'",
            "'D',   'CDXC', 'CMXC'"
        })
        void should_be_able_to_add_two_number_correctly(String number1, String number2, String actual) {
            assertEquals(of(number1).add(of(number2)).number(), actual);
        }
    }
}
