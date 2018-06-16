package com.techyourchance.testdoublesfundamentals.example5;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserInputValidatorTest {

    UserInputValidator SUT;

    @Before
    public void setup() throws Exception {
        SUT = new UserInputValidator();
    }

    @Test
    public void isValidFullName_validFullName_trueReturned() throws Exception {
        boolean result = SUT.isValidFullName("validFullName");
        assertThat(result, is(true));
    }

    @Test
    public void isValidFullName_invalidFullName_falseReturned() throws Exception {
        boolean result = SUT.isValidFullName("");
        assertThat(result, is(false));
    }

    @Test
    public void isValidUsername_validUsername_trueReturned() throws Exception {
        boolean result = SUT.isValidUsername("validUsername");
        assertThat(result, is(true));
    }

    @Test
    public void isValidUsername_invalidUsername_falseReturned() throws Exception {
        boolean result = SUT.isValidUsername("");
        assertThat(result, is(false));
    }
}