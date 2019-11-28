package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NegativeNumberValidatorTest {

    private NegativeNumberValidator SUT;

    @Before
    public void init() {
        SUT = new NegativeNumberValidator();
    }

    @Test
    public void test1() {
        boolean negative = SUT.isNegative(1);
        Assert.assertFalse(negative);
    }

    @Test
    public void test2() {
        boolean negative = SUT.isNegative(0);
        Assert.assertFalse(negative);
    }

    @Test
    public void test3() {
        boolean negative = SUT.isNegative(-1);
        Assert.assertTrue(negative);
    }

}