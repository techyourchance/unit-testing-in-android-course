package com.techyourchance.unittesting.screens.questionslist.questionslistitem;

import com.techyourchance.unittesting.questions.Question;
import com.techyourchance.unittesting.screens.common.views.ObservableViewMvc;

public interface QuestionsListItemViewMvc extends ObservableViewMvc<QuestionsListItemViewMvc.Listener> {

    public interface Listener {
        void onQuestionClicked(Question question);
    }

    void bindQuestion(Question question);
}
