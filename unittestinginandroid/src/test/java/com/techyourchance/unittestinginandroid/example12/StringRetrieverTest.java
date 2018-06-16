package com.techyourchance.unittestinginandroid.example12;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StringRetrieverTest {

    // region constants ----------------------------------------------------------------------------
    public static final int ID = 10;
    public static final String STRING = "string";
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    @Mock Context mContextMock;
    // endregion helper fields ---------------------------------------------------------------------

    StringRetriever SUT;

    @Before
    public void setup() throws Exception {
        SUT = new StringRetriever(mContextMock);
    }

    @Test
    public void getString_correctParameterPassedToContext() throws Exception {
        // Arrange
        // Act
        SUT.getString(ID);
        // Assert
        verify(mContextMock).getString(ID);
    }

    @Test
    public void getString_correctResultReturned() throws Exception {
        // Arrange
        when(mContextMock.getString(ID)).thenReturn(STRING);
        // Act
        String result = SUT.getString(ID);
        // Assert
        assertThat(result, is(STRING));
    }

    // region helper methods -----------------------------------------------------------------------
    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    // endregion helper classes --------------------------------------------------------------------

}