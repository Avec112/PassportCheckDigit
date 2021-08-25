package io.avec.checkdigit;

import io.avec.checkdigit.PassportCheckDigit;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PassportCheckDigitTest {

    private final PassportCheckDigit checkDigit = new PassportCheckDigit();

    @ParameterizedTest
    @CsvSource({
        "32823363,2", // passport no.
        "770720,5", // dob yymmdd
        "220513,1", // doe yymmdd
        "81977072046526,6" // ?yyyymmddppppp
    })
    void calculate(String input, String expected) throws CheckDigitException {
        assertEquals(expected, checkDigit.calculate(input));
    }

    @Test
    void calculateLong() throws CheckDigitException {
        String passportno = "328233632";
        String dob = "7707205";
        String doe = "2205131";
        String personalNo = "819770720465266";
        assertEquals("4", checkDigit.calculate(passportno + dob + doe + personalNo));
    }

    @ParameterizedTest
    @CsvSource({
            "21,3,1,3",
            "7,1,1,3",
            "9,3,2,2",
            "3,1,2,2",
            "9,9,3,1",
            "1,1,3,1",

    })
    void weightedValue(int expected, int charValue, int leftPos, int rightPos) {
        assertEquals(expected, checkDigit.weightedValue(charValue, leftPos, rightPos));
    }

    @ParameterizedTest
    @CsvSource({
            "0,'0'",
            "1,'1'",
            "9,'9'",
            "0,'<'"
    })
    void toInt(int expected, char character) throws CheckDigitException {
        assertEquals(expected, checkDigit.toInt(character, 0,0));
    }
}