package com.techyourchance.mockitofundamentals.example8;

public class PhoneNumber {
    private final String mCountryCode;
    private final String mNumber;

    public PhoneNumber(String countryCode, String number) {
        mCountryCode = countryCode;
        mNumber = number;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public String getNumber() {
        return mNumber;
    }
}
