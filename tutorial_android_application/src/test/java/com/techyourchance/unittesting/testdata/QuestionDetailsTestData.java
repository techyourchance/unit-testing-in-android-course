package com.techyourchance.unittesting.testdata;

import com.techyourchance.unittesting.questions.QuestionDetails;

public class QuestionDetailsTestData {

    public static QuestionDetails getQuestionDetails() {
        return new QuestionDetails("id", "title", "body");
    }
}
