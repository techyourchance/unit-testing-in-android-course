package com.techyourchance.unittesting.questions;

import java.util.Objects;

public class QuestionDetails {

    private final String mId;

    private final String mTitle;

    private final String mBody;

    public QuestionDetails(String id, String title, String body) {
        mId = id;
        mTitle = title;
        mBody = body;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDetails that = (QuestionDetails) o;
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mTitle, that.mTitle) &&
                Objects.equals(mBody, that.mBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle, mBody);
    }
}
