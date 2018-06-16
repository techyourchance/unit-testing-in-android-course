package com.techyourchance.mockitofundamentals.exercise5.users;

public interface UsersCache {

    void cacheUser(User user);

    User getUser(String userId);

}
