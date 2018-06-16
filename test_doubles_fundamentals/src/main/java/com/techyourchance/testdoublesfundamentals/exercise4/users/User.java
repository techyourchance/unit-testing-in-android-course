package com.techyourchance.testdoublesfundamentals.exercise4.users;

public class User {
    private final String mUserId;
    private final String mFullName;
    private final String mImageUrl;

    public User(String userId, String fullName, String imageUrl) {
        mUserId = userId;
        mFullName = fullName;
        mImageUrl = imageUrl;
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
