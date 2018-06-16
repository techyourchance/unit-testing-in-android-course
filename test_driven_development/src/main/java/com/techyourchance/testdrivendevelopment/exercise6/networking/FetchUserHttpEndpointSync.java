package com.techyourchance.testdrivendevelopment.exercise6.networking;


public interface FetchUserHttpEndpointSync {

    enum EndpointStatus {
        SUCCESS,
        AUTH_ERROR,
        GENERAL_ERROR
    }

    class EndpointResult {
        private final EndpointStatus mStatus;
        private final String mUserId;
        private final String mUsername;

        public EndpointResult(EndpointStatus status, String userId, String username) {
            mStatus = status;
            mUserId = userId;
            mUsername = username;
        }

        public EndpointStatus getStatus() {
            return mStatus;
        }

        public String getUserId() {
            return mUserId;
        }

        public String getUsername() {
            return mUsername;
        }
    }

    /**
     * Get user details
     * @return the aggregated result
     * @throws NetworkErrorException if operation failed due to network error
     */
    EndpointResult fetchUserSync(String userId) throws NetworkErrorException;

}
