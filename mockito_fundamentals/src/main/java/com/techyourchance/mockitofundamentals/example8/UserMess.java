package com.techyourchance.mockitofundamentals.example8;

import java.util.List;

public class UserMess {

    private final String mFullName;
    private final Address mAddress;
    private final PhoneNumber mPhoneNumber;

    public UserMess(String fullName, Address address, PhoneNumber phoneNumber) {
        mFullName = fullName;
        mAddress = address;
        mPhoneNumber = phoneNumber;
    }

    public void logOut() {
        // real implementation here
    }

    public void connectWith(UserMess otherUser) {
        // real implementation here
    }

    public List<UserMess> getConnectedUsers() {
        // real implementation here
        return null;
    }

    public void disconnectFromAll() {
        // real implementation here
    }

    public String getFullName() {
        return mFullName;
    }

    public Address getAddress() {
        return mAddress;
    }

    public PhoneNumber getPhoneNumber() {
        return mPhoneNumber;
    }
}
