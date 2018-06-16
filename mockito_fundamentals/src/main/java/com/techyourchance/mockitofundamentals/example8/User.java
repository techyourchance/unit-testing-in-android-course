package com.techyourchance.mockitofundamentals.example8;

public class User {
    private final String mFullName;
    private final Address mAddress;
    private final PhoneNumber mPhoneNumber;

    public User(String fullName, Address address, PhoneNumber phoneNumber) {
        mFullName = fullName;
        mAddress = address;
        mPhoneNumber = phoneNumber;
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
