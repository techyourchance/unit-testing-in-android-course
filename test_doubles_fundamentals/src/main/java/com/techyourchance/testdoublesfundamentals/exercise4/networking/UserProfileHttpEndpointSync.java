package com.techyourchance.testdoublesfundamentals.exercise4.networking;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;

public interface UserProfileHttpEndpointSync {

    /**
     * Get user's profile from the server
     * @return the aggregated result
     * @throws NetworkErrorException if operation failed due to network error
     */
    EndpointResult getUserProfile(String userId) throws NetworkErrorException;

    enum EndpointResultStatus {
        SUCCESS,
        AUTH_ERROR,
        SERVER_ERROR,
        GENERAL_ERROR
    }

    class EndpointResult {
        private final EndpointResultStatus mStatus;
        private final String mUserId;
        private final String mFullName;
        private final String mImageUrl;

        public EndpointResult(EndpointResultStatus status, String userId, String fullName, String imageUrl) {
            mStatus = status;
            mUserId = userId;
            mFullName = fullName;
            mImageUrl = imageUrl;
        }

        public EndpointResultStatus getStatus() {
            return mStatus;
        }

        public String getUserId() {
            return mUserId;
        }

        public String getFullName() {
            return mFullName;
        }

        public String getImageUrl() {
            return mImageUrl;
        }
    }
}
