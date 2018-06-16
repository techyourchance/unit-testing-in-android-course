package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class ExerciseSolution1 {

    NegativeNumberValidator SUT;

    @Before
    public void setup() {
        SUT = new NegativeNumberValidator();
    }

    @Test
    public void test1() {
        boolean result = SUT.isNegative(-1);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void test2() {
        boolean result = SUT.isNegative(0);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void test3() {
        boolean result = SUT.isNegative(1);
        Assert.assertThat(result, is(false));
    }
}