package com.techyourchance.mockitofundamentals.exercise5.users;

public class User {
    private final String mUserId;
    private final String mUsername;

    public User(String userId, String username) {
        mUserId = userId;
        mUsername = username;
    }

    public String getUserId() {
        return mUserId;
    }

    public String getUsername() {
        return mUsername;
    }
}
