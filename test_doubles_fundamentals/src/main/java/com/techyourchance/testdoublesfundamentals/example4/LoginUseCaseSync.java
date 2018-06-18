package com.techyourchance.testdoublesfundamentals.example4;

import com.techyourchance.testdoublesfundamentals.example4.authtoken.AuthTokenCache;
import com.techyourchance.testdoublesfundamentals.example4.eventbus.EventBusPoster;
import com.techyourchance.testdoublesfundamentals.example4.eventbus.LoggedInEvent;
import com.techyourchance.testdoublesfundamentals.example4.networking.LoginHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;

public class LoginUseCaseSync {

    public enum UseCaseResult {
        SUCCESS,
        FAILURE,
        NETWORK_ERROR
    }

    private final LoginHttpEndpointSync mLoginHttpEndpointSync;
    private final AuthTokenCache mAuthTokenCache;
    private final EventBusPoster mEventBusPoster;

    public LoginUseCaseSync(LoginHttpEndpointSync loginHttpEndpointSync,
                            AuthTokenCache authTokenCache,
                            EventBusPoster eventBusPoster) {
        mLoginHttpEndpointSync = loginHttpEndpointSync;
        mAuthTokenCache = authTokenCache;
        mEventBusPoster = eventBusPoster;
    }

    public UseCaseResult loginSync(String username, String password) {
        LoginHttpEndpointSync.EndpointResult endpointEndpointResult;
        try {
            endpointEndpointResult = mLoginHttpEndpointSync.loginSync(username, password);
        } catch (NetworkErrorException e) {
            return UseCaseResult.NETWORK_ERROR;
        }


        if (isSuccessfulEndpointResult(endpointEndpointResult)) {
            mAuthTokenCache.cacheAuthToken(endpointEndpointResult.getAuthToken());
            mEventBusPoster.postEvent(new LoggedInEvent());
            return UseCaseResult.SUCCESS;
        } else {
            return UseCaseResult.FAILURE;
        }
    }

    private boolean isSuccessfulEndpointResult(LoginHttpEndpointSync.EndpointResult endpointResult) {
        return endpointResult.getStatus() == LoginHttpEndpointSync.EndpointResultStatus.SUCCESS;
    }
}
