package com.techyourchance.testdrivendevelopment.example9.networking;

public interface AddToCartHttpEndpointSync {

    EndpointResult addToCartSync(CartItemScheme cartItemScheme) throws NetworkErrorException;

    enum EndpointResult {
        SUCCESS,
        AUTH_ERROR,
        GENERAL_ERROR
    }

}
