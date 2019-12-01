package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FetchUserProfileUseCaseSyncTest {

    private FetchUserProfileUseCaseSync SUT;
    private UserProfileHttpEndpointSyncImpl userProfileHttpEndpointSync;
    private UsersCacheImpl usersCache;

    private String USER_ID = "user";
    private String FULLNAME = "Fullname";
    private String IMAGEURL = "imageUrl";

    @Before
    public void setUp() {
        usersCache = new UsersCacheImpl();
        userProfileHttpEndpointSync = new UserProfileHttpEndpointSyncImpl();

        SUT = new FetchUserProfileUseCaseSync(userProfileHttpEndpointSync, usersCache);
    }

    @Test
    public void fetchUserProfileSync_correct_success() {
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        Assert.assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void fetchUserProfileSync_success_userIdPassedToEndpoint() {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(userProfileHttpEndpointSync.mUserId, is(USER_ID));
    }

    @Test
    public void fetchUserProfileSync_success_userStoredInCache() {
        SUT.fetchUserProfileSync(USER_ID);
        User cachedUser = usersCache.getUser(USER_ID);
        assertThat(Objects.requireNonNull(cachedUser).getUserId(), is(USER_ID));
        assertThat(cachedUser.getFullName(), is(FULLNAME));
        assertThat(cachedUser.getImageUrl(), is(IMAGEURL));
    }

    @Test
    public void fetchUserProfileSync_emptyUserId_generalError() {
        userProfileHttpEndpointSync.mIsGeneralError = true;
        FetchUserProfileUseCaseSync.UseCaseResult useCaseResult = SUT.fetchUserProfileSync("");
        Assert.assertThat(useCaseResult, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_serverError_failure() {
        userProfileHttpEndpointSync.mIsServerError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_networkError_failure() {
        userProfileHttpEndpointSync.mIsNetworkError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.NETWORK_ERROR));
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_authError_failure() {
        userProfileHttpEndpointSync.mIsAuthError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void fetchUserProfileSync_generalError_failure() {
        userProfileHttpEndpointSync.mIsGeneralError = true;
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }



//    ---------------------------------------------------------------------------------------------

    class UserProfileHttpEndpointSyncImpl implements UserProfileHttpEndpointSync {

        String mUserId = "";
        boolean mIsGeneralError;
        boolean mIsAuthError;
        boolean mIsServerError;
        boolean mIsNetworkError;

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            mUserId = userId;
            if (mIsGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", "", "");
            } else if (mIsAuthError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", "", "");
            }  else if (mIsServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", "", "");
            } else if (mIsNetworkError) {
                throw new NetworkErrorException();
            } else {
                return new EndpointResult(EndpointResultStatus.SUCCESS, USER_ID, FULLNAME, IMAGEURL);
            }
        }
    }

    class UsersCacheImpl implements UsersCache  {

        ArrayList<User> users = new ArrayList<>(1);

        @Override
        public void cacheUser(User user) {
            User existingUser = getUser(user.getUserId());
            if (existingUser != null) {
                users.remove(existingUser);
            } else {
                users.add(user);
            }
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            for (User user : users) {
                if (user.getUserId().equals(userId)) {
                    return user;
                }
            }
            return null;
        }
    }
}