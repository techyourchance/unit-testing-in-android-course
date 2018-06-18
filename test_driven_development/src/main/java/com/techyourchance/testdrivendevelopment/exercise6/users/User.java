package com.techyourchance.testdrivendevelopment.exercise6.users;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!mUserId.equals(user.mUserId)) return false;
        return mUsername.equals(user.mUsername);
    }

    @Override
    public int hashCode() {
        int result = mUserId.hashCode();
        result = 31 * result + mUsername.hashCode();
        return result;
    }
}
