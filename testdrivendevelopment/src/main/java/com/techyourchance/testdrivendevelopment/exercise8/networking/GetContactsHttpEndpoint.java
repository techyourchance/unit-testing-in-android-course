package com.techyourchance.testdrivendevelopment.exercise8.networking;

import java.util.List;

public interface GetContactsHttpEndpoint {

    enum FailReason {
        GENERAL_ERROR,
        NETWORK_ERROR
    }

    interface Callback {
        void onGetContactsSucceeded(List<ContactSchema> cartItems);
        void onGetContactsFailed(FailReason failReason);
    }

    /**
     * @param filterTerm filter term to match in any of the contact fields
     * @param callback object to be notified when the request completes
     */
    public void getContacts(String filterTerm, Callback callback);
}
