package com.techyourchance.unittestingfundamentals.exercise1;

public class NegativeNumberValidator {

    public boolean isNegative(int number) {
        // the bug is that 0 will be reported as negative while it's not
        return number < 0;
    }

}
