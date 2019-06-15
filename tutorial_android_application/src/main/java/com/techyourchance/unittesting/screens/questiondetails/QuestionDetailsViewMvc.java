package com.techyourchance.unittesting.screens.questiondetails;

import com.techyourchance.unittesting.questions.QuestionDetails;
import com.techyourchance.unittesting.screens.common.views.ObservableViewMvc;

public interface QuestionDetailsViewMvc extends ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    public interface Listener {
        void onNavigateUpClicked();
    }

    void bindQuestion(QuestionDetails question);

    void showProgressIndication();

    void hideProgressIndication();
}
