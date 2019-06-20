package com.techyourchance.unittesting.testdata;

import com.techyourchance.unittesting.questions.Question;

import java.util.LinkedList;
import java.util.List;

public class QuestionsTestData {

    public static Question getQuestion() {
        return new Question("id", "title");
    }

    public static List<Question> getQuestions() {
        List<Question> questions = new LinkedList<>();
        questions.add(new Question("id1", "title1"));
        questions.add(new Question("id2", "title2"));
        return questions;
    }
}
