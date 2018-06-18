package com.techyourchance.testdrivendevelopment.exercise8.contacts;

public class Contact {

    private final String mId;
    private final String mFullName;
    private final String mImageUrl;

    public Contact(String id, String fullName, String imageUrl) {
        mId = id;
        mFullName = fullName;
        mImageUrl = imageUrl;
    }

    public String getId() {
        return mId;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
