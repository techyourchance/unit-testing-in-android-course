package com.techyourchance.unittesting.screens.questiondetails;

import com.techyourchance.unittesting.networking.questions.FetchQuestionDetailsEndpoint;
import com.techyourchance.unittesting.questions.FetchQuestionDetailsUseCase;
import com.techyourchance.unittesting.questions.QuestionDetails;
import com.techyourchance.unittesting.screens.common.screensnavigator.ScreensNavigator;
import com.techyourchance.unittesting.screens.common.toastshelper.ToastsHelper;
import com.techyourchance.unittesting.testdata.QuestionDetailsTestData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionDetailsControllerSolutionTest {

    // region constants ----------------------------------------------------------------------------
    private static final QuestionDetails QUESTION_DETAILS = QuestionDetailsTestData.getQuestionDetails();
    private static final String QUESTION_ID = QUESTION_DETAILS.getId();
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    private UseCaseTd mUseCaseTd;
    @Mock ScreensNavigator mScreensNavigatorMock;
    @Mock ToastsHelper mToastsHelperMock;
    @Mock QuestionDetailsViewMvc mQuestionDetailsViewMvcMock;
    // endregion helper fields ---------------------------------------------------------------------

    QuestionDetailsController SUT;

    @Before
    public void setup() throws Exception {
        mUseCaseTd = new UseCaseTd();
        SUT = new QuestionDetailsController(mUseCaseTd, mScreensNavigatorMock, mToastsHelperMock);
        SUT.bindView(mQuestionDetailsViewMvcMock);
        SUT.bindQuestionId(QUESTION_ID);
    }

    @Test
    public void onStart_listenersRegistered() throws Exception {
        // Arrange
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionDetailsViewMvcMock).registerListener(SUT);
        mUseCaseTd.verifyListenerRegistered(SUT);
    }

    @Test
    public void onStop_listenersUnregistered() throws Exception {
        // Arrange
        SUT.onStart();
        // Act
        SUT.onStop();
        // Assert
        verify(mQuestionDetailsViewMvcMock).unregisterListener(SUT);
        mUseCaseTd.verifyListenerNotRegistered(SUT);
    }

    @Test
    public void onStart_success_questionDetailsBoundToView() throws Exception {
        // Arrange
        success();
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionDetailsViewMvcMock).bindQuestion(QUESTION_DETAILS);
    }

    @Test
    public void onStart_failure_errorToastShown() throws Exception {
        // Arrange
        failure();
        // Act
        SUT.onStart();
        // Assert
        verify(mToastsHelperMock).showUseCaseError();
    }

    @Test
    public void onStart_progressIndicationShown() throws Exception {
        // Arrange
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionDetailsViewMvcMock).showProgressIndication();
    }

    @Test
    public void onStart_success_progressIndicationHidden() throws Exception {
        // Arrange
        success();
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionDetailsViewMvcMock).hideProgressIndication();
    }

    @Test
    public void onStart_failure_progressIndicationShown() throws Exception {
        // Arrange
        failure();
        // Act
        SUT.onStart();
        // Assert
        verify(mQuestionDetailsViewMvcMock).hideProgressIndication();
    }

    @Test
    public void onNavigateUpClicked_navigatedUp() throws Exception {
        // Arrange
        // Act
        SUT.onNavigateUpClicked();
        // Assert
        verify(mScreensNavigatorMock).navigateUp();
    }

    // region helper methods -----------------------------------------------------------------------

    private void success() {
        // currently no-op
    }

    private void failure() {
        mUseCaseTd.mFailure = true;
    }

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------

    private static class UseCaseTd extends FetchQuestionDetailsUseCase {

        private boolean mFailure;

        public UseCaseTd() {
            super(null);
        }

        @Override
        public void fetchQuestionDetailsAndNotify(String questionId) {
            if (!questionId.equals(QUESTION_ID)) {
                throw new RuntimeException("invalid question ID: " + questionId);
            }
            for (Listener listener : getListeners()) {
                if (mFailure) {
                    listener.onQuestionDetailsFetchFailed();
                } else {
                    listener.onQuestionDetailsFetched(QUESTION_DETAILS);
                }
            }
        }

        public void verifyListenerRegistered(QuestionDetailsController candidate) {
            for (Listener listener : getListeners()) {
                if (listener == candidate) {
                    return;
                }
            }
            throw new RuntimeException("listener not registered");
        }

        public void verifyListenerNotRegistered(QuestionDetailsController candidate) {
            for (Listener listener : getListeners()) {
                if (listener == candidate) {
                    throw new RuntimeException("listener registered");
                }
            }
        }
    }

    // endregion helper classes --------------------------------------------------------------------

}