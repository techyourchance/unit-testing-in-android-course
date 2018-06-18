package com.techyourchance.mockitofundamentals.example8;

public class Address {
    private final String mCountry;
    private final String mCity;
    private final String mStreet;
    private final int mHomeNumber;

    public Address(String country, String city, String street, int homeNumber) {
        mCountry = country;
        mCity = city;
        mStreet = street;
        mHomeNumber = homeNumber;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCity() {
        return mCity;
    }

    public String getStreet() {
        return mStreet;
    }

    public int getHomeNumber() {
        return mHomeNumber;
    }
}
