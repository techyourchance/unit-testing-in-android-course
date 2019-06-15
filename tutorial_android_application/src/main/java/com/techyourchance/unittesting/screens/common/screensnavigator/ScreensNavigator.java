package com.techyourchance.unittesting.screens.common.screensnavigator;

import com.techyourchance.unittesting.screens.common.fragmentframehelper.FragmentFrameHelper;
import com.techyourchance.unittesting.screens.questiondetails.QuestionDetailsFragment;
import com.techyourchance.unittesting.screens.questionslist.QuestionsListFragment;

public class ScreensNavigator {

    private FragmentFrameHelper mFragmentFrameHelper;

    public ScreensNavigator(FragmentFrameHelper fragmentFrameHelper) {
        mFragmentFrameHelper = fragmentFrameHelper;
    }

    public void toQuestionDetails(String questionId) {
        mFragmentFrameHelper.replaceFragment(QuestionDetailsFragment.newInstance(questionId));
    }

    public void toQuestionsList() {
        mFragmentFrameHelper.replaceFragmentAndClearBackstack(QuestionsListFragment.newInstance());
    }

    public void navigateUp() {
        mFragmentFrameHelper.navigateUp();
    }
}
