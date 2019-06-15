package com.techyourchance.unittesting.screens.common;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.techyourchance.unittesting.screens.common.navdrawer.NavDrawerHelper;
import com.techyourchance.unittesting.screens.common.navdrawer.NavDrawerViewMvc;
import com.techyourchance.unittesting.screens.common.navdrawer.NavDrawerViewMvcImpl;
import com.techyourchance.unittesting.screens.common.toolbar.ToolbarViewMvc;
import com.techyourchance.unittesting.screens.questiondetails.QuestionDetailsViewMvc;
import com.techyourchance.unittesting.screens.questiondetails.QuestionDetailsViewMvcImpl;
import com.techyourchance.unittesting.screens.questionslist.QuestionsListViewMvc;
import com.techyourchance.unittesting.screens.questionslist.QuestionsListViewMvcImpl;
import com.techyourchance.unittesting.screens.questionslist.questionslistitem.QuestionsListItemViewMvc;
import com.techyourchance.unittesting.screens.questionslist.questionslistitem.QuestionsListItemViewMvcImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private final NavDrawerHelper mNavDrawerHelper;

    public ViewMvcFactory(LayoutInflater layoutInflater, NavDrawerHelper navDrawerHelper) {
        mLayoutInflater = layoutInflater;
        mNavDrawerHelper = navDrawerHelper;
    }

    public QuestionsListViewMvc getQuestionsListViewMvc(@Nullable ViewGroup parent) {
        return new QuestionsListViewMvcImpl(mLayoutInflater, parent, mNavDrawerHelper, this);
    }

    public QuestionsListItemViewMvc getQuestionsListItemViewMvc(@Nullable ViewGroup parent) {
        return new QuestionsListItemViewMvcImpl(mLayoutInflater, parent);
    }

    public QuestionDetailsViewMvc getQuestionDetailsViewMvc(@Nullable ViewGroup parent) {
        return new QuestionDetailsViewMvcImpl(mLayoutInflater, parent, this);
    }

    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvc(mLayoutInflater, parent);
    }

    public NavDrawerViewMvc getNavDrawerViewMvc(@Nullable ViewGroup parent) {
        return new NavDrawerViewMvcImpl(mLayoutInflater, parent);
    }
}
