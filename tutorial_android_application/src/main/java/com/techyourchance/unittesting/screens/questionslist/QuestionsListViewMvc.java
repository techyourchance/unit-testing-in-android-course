package com.techyourchance.unittesting.screens.questionslist;

import com.techyourchance.unittesting.questions.Question;
import com.techyourchance.unittesting.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface QuestionsListViewMvc extends ObservableViewMvc<QuestionsListViewMvc.Listener> {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestions(List<Question> questions);

    void showProgressIndication();

    void hideProgressIndication();

}
