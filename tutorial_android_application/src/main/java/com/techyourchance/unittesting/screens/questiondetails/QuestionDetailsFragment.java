package com.techyourchance.unittesting.screens.questiondetails;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.unittesting.screens.common.controllers.BaseFragment;

public class QuestionDetailsFragment extends BaseFragment {

    private static final String ARG_QUESTION_ID = "ARG_QUESTION_ID";

    public static QuestionDetailsFragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private QuestionDetailsController mQuestionDetailsController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestionDetailsController = getCompositionRoot().getQuestionDetailsController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        QuestionDetailsViewMvc mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(container);

        mQuestionDetailsController.bindView(mViewMvc);
        mQuestionDetailsController.bindQuestionId(getArguments().getString(ARG_QUESTION_ID));

        return mViewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mQuestionDetailsController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mQuestionDetailsController.onStop();
    }

    private String getQuestionId() {
        return getArguments().getString(ARG_QUESTION_ID);
    }

}
