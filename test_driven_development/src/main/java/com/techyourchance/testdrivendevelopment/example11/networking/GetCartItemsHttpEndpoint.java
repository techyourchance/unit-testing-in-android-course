package com.techyourchance.testdrivendevelopment.example11.networking;

import java.util.List;

public interface GetCartItemsHttpEndpoint {

    enum FailReason {
        GENERAL_ERROR,
        NETWORK_ERROR
    }

    interface Callback {
        void onGetCartItemsSucceeded(List<CartItemSchema> cartItems);
        void onGetCartItemsFailed(FailReason failReason);
    }

    /**
     * @param limit max amount of cart items to fetch
     * @param callback object to be notified when the request completes
     */
    public void getCartItems(int limit, Callback callback);
}
