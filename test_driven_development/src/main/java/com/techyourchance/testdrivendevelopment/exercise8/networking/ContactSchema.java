package com.techyourchance.testdrivendevelopment.exercise8.networking;

public class ContactSchema {
    private final String mId;
    private final String mFullName;
    private final String mFullPhoneNumber;
    private final String mImageUrl;
    private final double mAge;

    public ContactSchema(String id, String fullName, String fullPhoneNumber, String imageUrl, double age) {
        mId = id;
        mFullName = fullName;
        mFullPhoneNumber = fullPhoneNumber;
        mImageUrl = imageUrl;
        mAge = age;
    }

    public String getId() {
        return mId;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getFullPhoneNumber() {
        return mFullPhoneNumber;
    }

    public double getAge() {
        return mAge;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
