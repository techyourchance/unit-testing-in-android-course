package com.techyourchance.unittesting.questions;

import java.util.Objects;

public class Question {

    private final String mId;

    private final String mTitle;

    public Question(String id, String title) {
        mId = id;
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(mId, question.mId) &&
                Objects.equals(mTitle, question.mTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mTitle);
    }
}
