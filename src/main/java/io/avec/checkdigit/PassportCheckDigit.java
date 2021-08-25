package io.avec.checkdigit;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.ModulusCheckDigit;

public class PassportCheckDigit extends ModulusCheckDigit {

    private final static int modulus = 10;
    private final int[] postitionWeight = {7,3,1};

    public PassportCheckDigit() {
        super(modulus);
    }

    @Override
    public String calculate(String code) throws CheckDigitException {
        if (code == null || code.length() == 0) {
            throw new CheckDigitException("Code is missing");
        }
        int modulusResult = calculateModulus(code, false);
        return toCheckDigit(modulusResult);
    }


    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) {
        int weight = postitionWeight[(leftPos - 1) % postitionWeight.length];
        return charValue * weight;
    }

    @Override
    protected int toInt(char character, int leftPos, int rightPos)
            throws CheckDigitException {
        if (Character.isDigit(character)) {
            return Character.getNumericValue(character);
        } else if(character == '<') { // passport placeholder
            return 0;
        }
        throw new CheckDigitException("Invalid Character[" +
                leftPos + "] = '" + character + "'");
    }
}
