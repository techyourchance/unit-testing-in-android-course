package com.techyourchance.unittesting.screens.questionslist;

import com.techyourchance.unittesting.common.time.TimeProvider;
import com.techyourchance.unittesting.questions.FetchLastActiveQuestionsUseCase;
import com.techyourchance.unittesting.questions.Question;
import com.techyourchance.unittesting.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.unittesting.screens.common.toastshelper.ToastsHelper;

import java.util.List;

public class QuestionsListController  implements
        QuestionsListViewMvc.Listener,
        FetchLastActiveQuestionsUseCase.Listener {

    private static final int CACHE_TIMEOUT_MS = 10000;

    private final FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private final ScreensNavigator mScreensNavigator;
    private final ToastsHelper mToastsHelper;
    private final TimeProvider mTimeProvider;

    private QuestionsListViewMvc mViewMvc;
    private List<Question> mQuestions;
    private long mLastCachedTimestamp;

    public QuestionsListController(FetchLastActiveQuestionsUseCase fetchLastActiveQuestionsUseCase,
                                   ScreensNavigator screensNavigator,
                                   ToastsHelper toastsHelper,
                                   TimeProvider timeProvider) {
        mFetchLastActiveQuestionsUseCase = fetchLastActiveQuestionsUseCase;
        mScreensNavigator = screensNavigator;
        mToastsHelper = toastsHelper;
        mTimeProvider = timeProvider;
    }

    public void bindView(QuestionsListViewMvc viewMvc) {
        mViewMvc = viewMvc;
    }

    public void onStart() {
        mViewMvc.registerListener(this);
        mFetchLastActiveQuestionsUseCase.registerListener(this);

        if (isCachedDataValid()) {
            mViewMvc.bindQuestions(mQuestions);
        } else {
            mViewMvc.showProgressIndication();
            mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestionsAndNotify();
        }
    }

    private boolean isCachedDataValid() {
        return mQuestions != null
                && mTimeProvider.getCurrentTimestamp() < mLastCachedTimestamp + CACHE_TIMEOUT_MS;
    }

    public void onStop() {
        mViewMvc.unregisterListener(this);
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
    }

    @Override
    public void onQuestionClicked(Question question) {
        mScreensNavigator.toQuestionDetails(question.getId());
    }
    
    @Override
    public void onLastActiveQuestionsFetched(List<Question> questions) {
        mQuestions = questions;
        mLastCachedTimestamp = mTimeProvider.getCurrentTimestamp();
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onLastActiveQuestionsFetchFailed() {
        mViewMvc.hideProgressIndication();
        mToastsHelper.showUseCaseError();
    }
}
