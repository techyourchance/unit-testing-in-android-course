package com.techyourchance.testdrivendevelopment.example10;

import com.techyourchance.testdrivendevelopment.example10.networking.PingServerHttpEndpointSync;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PingServerSyncUseCaseTest {

    // region constants ----------------------------------------------------------------------------
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    @Mock PingServerHttpEndpointSync mPingServerHttpEndpointSyncMock;
    // endregion helper fields ---------------------------------------------------------------------

    PingServerSyncUseCase SUT;

    @Before
    public void setup() throws Exception {
        SUT = new PingServerSyncUseCase(mPingServerHttpEndpointSyncMock);
        success();
    }

    @Test
    public void pingServerSync_success_successReturned() throws Exception {
        // Arrange
        // Act
        PingServerSyncUseCase.UseCaseResult result = SUT.pingServerSync();
        // Assert
        assertThat(result, is(PingServerSyncUseCase.UseCaseResult.SUCCESS));
    }

    @Test
    public void pingServerSync_generalError_failureReturned() throws Exception {
        // Arrange
        generalError();
        // Act
        PingServerSyncUseCase.UseCaseResult result = SUT.pingServerSync();
        // Assert
        assertThat(result, is(PingServerSyncUseCase.UseCaseResult.FAILURE));
    }

    @Test
    public void pingServerSync_networkError_failureReturned() throws Exception {
        // Arrange
        networkError();
        // Act
        PingServerSyncUseCase.UseCaseResult result = SUT.pingServerSync();
        // Assert
        assertThat(result, is(PingServerSyncUseCase.UseCaseResult.FAILURE));
    }

    // region helper methods -----------------------------------------------------------------------

    private void success() {
        when(mPingServerHttpEndpointSyncMock.pingServerSync()).thenReturn(PingServerHttpEndpointSync.EndpointResult.SUCCESS);
    }

    private void networkError() {
        when(mPingServerHttpEndpointSyncMock.pingServerSync()).thenReturn(PingServerHttpEndpointSync.EndpointResult.NETWORK_ERROR);
    }

    private void generalError() {
        when(mPingServerHttpEndpointSyncMock.pingServerSync()).thenReturn(PingServerHttpEndpointSync.EndpointResult.GENERAL_ERROR);
    }

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    // endregion helper classes --------------------------------------------------------------------

}