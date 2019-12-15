package com.techyourchance.mockitofundamentals.exercise5.networking;

public interface UpdateUsernameHttpEndpointSync {

    /**
     * Update user's username on the server
     * @return the aggregated result
     * @throws NetworkErrorException if operation failed due to network error
     */
    EndpointResult updateUsername(String userId, String username) throws NetworkErrorException;

    enum EndpointResultStatus {
        SUCCESS,
        AUTH_ERROR,
        SERVER_ERROR,
        GENERAL_ERROR
    }

    class EndpointResult {
        private final EndpointResultStatus mStatus;
        private final String mUserId;
        private final String mUsername;

        public EndpointResult(EndpointResultStatus status, String userId, String username) {
            mStatus = status;
            mUserId = userId;
            mUsername = username;
        }

        public EndpointResultStatus getStatus() {
            return mStatus;
        }

        public String getUserId() {
            return mUserId;
        }

        public String getUsername() {
            return mUsername;
        }
    }
}
