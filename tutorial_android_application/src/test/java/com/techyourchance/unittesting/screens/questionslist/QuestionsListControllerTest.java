package com.techyourchance.unittesting.screens.questionslist;

import com.techyourchance.unittesting.questions.FetchLastActiveQuestionsUseCase;
import com.techyourchance.unittesting.questions.Question;
import com.techyourchance.unittesting.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.unittesting.screens.common.toastshelper.ToastsHelper;
import com.techyourchance.unittesting.testdata.QuestionsTestData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuestionsListControllerTest {

    // region constants ----------------------------------------------------------------------------
    private static final List<Question> QUESTIONS = QuestionsTestData.getQuestions();
    private static final Question QUESTION = QuestionsTestData.getQuestion();
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    private UseCaseTd mUseCaseTd;
    @Mock ScreensNavigator mScreensNavigator;
    @Mock ToastsHelper mToastsHelper;
    @Mock QuestionsListViewMvc mQuestionsListViewMvc;
    // endregion helper fields ---------------------------------------------------------------------

    QuestionsListController SUT;

    @Before
    public void setup() throws Exception {
        mUseCaseTd = new UseCaseTd();
        SUT = new QuestionsListController(mUseCaseTd, mScreensNavigator, mToastsHelper);
        SUT.bindView(mQuestionsListViewMvc);
    }

    @Test
    public void onStart_progressIndicationShown() throws Exception {
        // Arrange
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionsListViewMvc).showProgressIndication();
    }

    @Test
    public void onStart_successfulResponse_progressIndicationHidden() throws Exception {
        // Arrange
        success();
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionsListViewMvc).hideProgressIndication();
    }

    @Test
    public void onStart_failure_progressIndicationHidden() throws Exception {
        // Arrange
        failure();
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionsListViewMvc).hideProgressIndication();
    }

    @Test
    public void onStart_successfulResponse_questionsBoundToView() throws Exception {
        // Arrange
        success();
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionsListViewMvc).bindQuestions(QUESTIONS);
    }

    @Test
    public void onStart_secondTimeAfterSuccessfulResponse_questionsBoundToTheViewFromCache() throws Exception {
        // Arrange
        success();
        // Act
        SUT.onStart();
        SUT.onStart();
        // Assert
        verify(mQuestionsListViewMvc, times(2)).bindQuestions(QUESTIONS);
        assertThat(mUseCaseTd.getCallCount(), is(1));
    }

    @Test
    public void onStart_failure_errorToastShown() throws Exception {
        // Arrange
        failure();
        // Act
        SUT.onStart();
        // Assert
        verify(mToastsHelper).showUseCaseError();
    }

    @Test
    public void onStart_failure_questionsNotBoundToView() throws Exception {
        // Arrange
        failure();
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionsListViewMvc, never()).bindQuestions(any(List.class));
    }

    @Test
    public void onStart_listenersRegistered() throws Exception {
        // Arrange
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionsListViewMvc).registerListener(SUT);
        mUseCaseTd.verifyListenerRegistered(SUT);
    }

    @Test
    public void onStop_listenersUnregistered() throws Exception {
        // Arrange
        // Act
        SUT.onStop();
        // Assert
        verify(mQuestionsListViewMvc).unregisterListener(SUT);
        mUseCaseTd.verifyListenerNotRegistered(SUT);
    }

    @Test
    public void onQuestionClicked_navigatedToQuestionDetailsScreen() throws Exception {
        // Arrange
        // Act
        SUT.onQuestionClicked(QUESTION);
        // Assert
        verify(mScreensNavigator).toQuestionDetails(QUESTION.getId());
    }


    // region helper methods -----------------------------------------------------------------------

    private void success() {
        // currently no-op
    }

    private void failure() {
        mUseCaseTd.mFailure = true;
    }

    private List<Question> getExpectedQuestions() {
        List<Question> questions = new LinkedList<>();
        questions.add(new Question("id1", "title1"));
        questions.add(new Question("id2", "title2"));
        return questions;
    }
    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    private static class UseCaseTd extends FetchLastActiveQuestionsUseCase {

        private boolean mFailure;
        private int mCallCount;

        public UseCaseTd() {
            super(null);
        }

        @Override
        public void fetchLastActiveQuestionsAndNotify() {
            mCallCount++;
            for (FetchLastActiveQuestionsUseCase.Listener listener : getListeners()) {
                if (mFailure) {
                    listener.onLastActiveQuestionsFetchFailed();
                } else {
                    listener.onLastActiveQuestionsFetched(QUESTIONS);
                }
            }
        }

        public void verifyListenerRegistered(QuestionsListController candidate) {
            for (FetchLastActiveQuestionsUseCase.Listener listener : getListeners()) {
                if (listener == candidate) {
                    return;
                }
            }
            throw new RuntimeException("listener not registered");
        }

        public void verifyListenerNotRegistered(QuestionsListController candidate) {
            for (FetchLastActiveQuestionsUseCase.Listener listener : getListeners()) {
                if (listener == candidate) {
                    throw new RuntimeException("listener not registered");
                }
            }
        }

        public int getCallCount() {
            return mCallCount;
        }
    }
    // endregion helper classes --------------------------------------------------------------------

}