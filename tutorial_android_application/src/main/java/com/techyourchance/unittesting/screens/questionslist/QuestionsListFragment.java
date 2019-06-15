package com.techyourchance.unittesting.screens.questionslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techyourchance.unittesting.screens.common.controllers.BaseFragment;

public class QuestionsListFragment extends BaseFragment {

    public static Fragment newInstance() {
        return new QuestionsListFragment();
    }

    private QuestionsListController mQuestionsListController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestionsListController = getCompositionRoot().getQuestionsListController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        QuestionsListViewMvc viewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(container);

        mQuestionsListController.bindView(viewMvc);

        return viewMvc.getRootView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mQuestionsListController.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mQuestionsListController.onStop();
    }

}
