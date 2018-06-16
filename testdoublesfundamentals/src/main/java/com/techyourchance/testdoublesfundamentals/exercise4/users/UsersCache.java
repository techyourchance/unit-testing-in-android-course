package com.techyourchance.testdoublesfundamentals.exercise4.users;

public interface UsersCache {

    void cacheUser(User user);

    User getUser(String userId);

}
