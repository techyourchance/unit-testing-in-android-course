package com.techyourchance.mockitofundamentals.exercise5;

import com.techyourchance.mockitofundamentals.exercise5.eventbus.EventBusPoster;
import com.techyourchance.mockitofundamentals.exercise5.eventbus.UserDetailsChangedEvent;
import com.techyourchance.mockitofundamentals.exercise5.networking.NetworkErrorException;
import com.techyourchance.mockitofundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync;
import com.techyourchance.mockitofundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync.EndpointResult;
import com.techyourchance.mockitofundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync.EndpointResultStatus;
import com.techyourchance.mockitofundamentals.exercise5.users.User;
import com.techyourchance.mockitofundamentals.exercise5.users.UsersCache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static com.techyourchance.mockitofundamentals.exercise5.UpdateUsernameUseCaseSync.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UpdateUsernameUseCaseSyncTest {

    private static String USERID = "userid";
    private static String USERNAME = "username";

    private UpdateUsernameUseCaseSync SUT;

    private EventBusPoster eventBusPoster;
    private UpdateUsernameHttpEndpointSync updateUsernameHttpEndpointSync;
    private UsersCache usersCache;

    @Before
    public void setUp() throws Exception {
        eventBusPoster = Mockito.mock(EventBusPoster.class);
        updateUsernameHttpEndpointSync = Mockito.mock(UpdateUsernameHttpEndpointSync.class);
        usersCache = Mockito.mock(UsersCache.class);
        SUT = new UpdateUsernameUseCaseSync(updateUsernameHttpEndpointSync,
                usersCache, eventBusPoster);
        success();
    }

    @Test
    public void updateUsernameSync_success() {
        UseCaseResult useCaseResult = SUT.updateUsernameSync(USERID, USERNAME);
        Assert.assertThat(useCaseResult, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void updateUsernameSync_success_endpointSuccess() throws NetworkErrorException {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.updateUsernameSync(USERID, USERNAME);
        verify(updateUsernameHttpEndpointSync, times(1)).updateUsername(ac.capture(), ac.capture());
        List<String> allValues = ac.getAllValues();
        Assert.assertThat(allValues.get(0), is(USERID));
        Assert.assertThat(allValues.get(1), is(USERNAME));
    }

    @Test
    public void updateUsernameSync_success_eventSuccess() {
        ArgumentCaptor<Object> ac = ArgumentCaptor.forClass(Object.class);
        SUT.updateUsernameSync(USERID, USERNAME);
        verify(eventBusPoster).postEvent(ac.capture());
        Assert.assertThat(ac.getValue(), is(instanceOf(UserDetailsChangedEvent.class)));
    }

    @Test
    public void updateUsernameSync_success_userCacheSuccess() {
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        SUT.updateUsernameSync(USERID, USERNAME);
        verify(usersCache).cacheUser(ac.capture());
        User cachedUser = ac.getValue();
        Assert.assertThat(cachedUser.getUserId(), is(USERID));
        Assert.assertThat(cachedUser.getUsername(), is(USERNAME));
    }

    @Test
    public void updateUsernameSync_failure_networkFailure() throws NetworkErrorException {
        failureNetwork();
        UseCaseResult result = SUT.updateUsernameSync(USERID, USERNAME);
        Assert.assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    @Test
    public void updateUsernameSync_failure_authError() throws NetworkErrorException {
        failureAuthError();
        UseCaseResult result = SUT.updateUsernameSync(USERID, USERNAME);
        Assert.assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void updateUsernameSync_failure_generalError() throws NetworkErrorException {
        failureGeneralError();
        UseCaseResult result = SUT.updateUsernameSync(USERID, USERNAME);
        Assert.assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void updateUsernameSync_failure_serverError() throws NetworkErrorException {
        failureServerError();
        UseCaseResult result = SUT.updateUsernameSync(USERID, USERNAME);
        Assert.assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void updateUsernameSync_generalError_noInteractionWithEventBusPoster() throws Exception {
        failureGeneralError();
        SUT.updateUsernameSync(USERID, USERNAME);
        verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUsernameSync_authError_noInteractionWithEventBusPoster() throws Exception {
        failureAuthError();
        SUT.updateUsernameSync(USERID, USERNAME);
        verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUsernameSync_serverError_noInteractionWithEventBusPoster() throws Exception {
        failureServerError();
        SUT.updateUsernameSync(USERID, USERNAME);
        verifyNoMoreInteractions(eventBusPoster);
    }

    @Test
    public void updateUsernameSync_generalError_noInteractionWithUsersCache() throws Exception {
        failureGeneralError();
        SUT.updateUsernameSync(USERID, USERNAME);
        verifyNoMoreInteractions(usersCache);
    }

    @Test
    public void updateUsernameSync_authError_noInteractionWithUsersCache() throws Exception {
        failureAuthError();
        SUT.updateUsernameSync(USERID, USERNAME);
        verifyNoMoreInteractions(usersCache);
    }

    @Test
    public void updateUsernameSync_serverError_noInteractionWithUsersCache() throws Exception {
        failureServerError();
        SUT.updateUsernameSync(USERID, USERNAME);
        verifyNoMoreInteractions(usersCache);
    }

    // _____________________________________________________________________________________________

    private void success() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.SUCCESS, USERID, USERNAME));
    }

    private void failureAuthError() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", ""));
    }

    private void failureGeneralError() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", ""));
    }

    private void failureServerError() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSync.updateUsername(anyString(), anyString()))
                .thenReturn(new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", ""));
    }

    private void failureNetwork() throws NetworkErrorException {
        doThrow(new NetworkErrorException())
                .when(updateUsernameHttpEndpointSync).updateUsername(anyString(), anyString());
    }
}