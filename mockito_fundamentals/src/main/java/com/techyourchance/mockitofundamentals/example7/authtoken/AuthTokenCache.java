package com.techyourchance.mockitofundamentals.example7.authtoken;

public interface AuthTokenCache {

    void cacheAuthToken(String authToken);

    String getAuthToken();
}
