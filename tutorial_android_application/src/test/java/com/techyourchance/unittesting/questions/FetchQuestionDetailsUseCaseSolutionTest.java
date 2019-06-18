package com.techyourchance.unittesting.questions;

import com.techyourchance.unittesting.networking.questions.FetchQuestionDetailsEndpoint;
import com.techyourchance.unittesting.networking.questions.QuestionSchema;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FetchQuestionDetailsUseCaseSolutionTest {

    // region constants ----------------------------------------------------------------------------
    private static final String QUESTION_ID = "questionId";
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    @Mock FetchQuestionDetailsEndpoint mFetchQuestionDetailsEndpointMock;
    private ListenerTd mListener1;
    private ListenerTd mListener2;
    // endregion helper fields ---------------------------------------------------------------------

    FetchQuestionDetailsUseCase SUT;

    @Before
    public void setup() throws Exception {
        mListener1 = new ListenerTd();
        mListener2 = new ListenerTd();
        SUT = new FetchQuestionDetailsUseCase(mFetchQuestionDetailsEndpointMock);
    }

    @Test
    public void fetchLastActiveQuestionsAndNotify_success_listenersNotifiedWithCorrectData() throws Exception {
        // Arrange
        success();
        SUT.registerListener(mListener1);
        SUT.registerListener(mListener2);
        // Act
        SUT.fetchQuestionDetailsAndNotify(QUESTION_ID);
        // Assert
        QuestionDetails expected = new QuestionDetails("id", "title", "body");
        mListener1.assertOneSuccessfulCall();
        assertThat(mListener1.getData(), is(expected));
        mListener2.assertOneSuccessfulCall();
        assertThat(mListener2.getData(), is(expected));
    }

    @Test
    public void fetchLastActiveQuestionsAndNotify_failure_listenersNotifiedOfFailure() throws Exception {
        // Arrange
        failure();
        SUT.registerListener(mListener1);
        SUT.registerListener(mListener2);
        // Act
        SUT.fetchQuestionDetailsAndNotify(QUESTION_ID);
        // Assert
        mListener1.assertOneFailingCall();
        mListener2.assertOneFailingCall();
    }

    // region helper methods -----------------------------------------------------------------------

    private void success() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                FetchQuestionDetailsEndpoint.Listener listener =
                        (FetchQuestionDetailsEndpoint.Listener) args[1];
                listener.onQuestionDetailsFetched(new QuestionSchema("title", "id", "body"));
                return null;
            }
        }).when(mFetchQuestionDetailsEndpointMock).fetchQuestionDetails(
                eq(QUESTION_ID),
                any(FetchQuestionDetailsEndpoint.Listener.class)
        );
    }

    private void failure() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                FetchQuestionDetailsEndpoint.Listener listener =
                        (FetchQuestionDetailsEndpoint.Listener) args[1];
                listener.onQuestionDetailsFetchFailed();
                return null;
            }
        }).when(mFetchQuestionDetailsEndpointMock).fetchQuestionDetails(
                eq(QUESTION_ID),
                any(FetchQuestionDetailsEndpoint.Listener.class)
        );
    }

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    private static class ListenerTd implements FetchQuestionDetailsUseCase.Listener {
        private int mCallCount;
        private boolean mSuccess;
        private QuestionDetails mData;

        @Override
        public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
            mCallCount++;
            mSuccess = true;
            mData = questionDetails;
        }

        @Override
        public void onQuestionDetailsFetchFailed() {
            mCallCount++;
            mSuccess = false;
        }

        public void assertOneSuccessfulCall() {
            if (mCallCount != 1 || !mSuccess) {
                throw new RuntimeException("one successful call assertion failed");
            }
        }

        public void assertOneFailingCall() {
            if (mCallCount != 1 || mSuccess) {
                throw new RuntimeException("one failing call assertion failed");
            }
        }

        public QuestionDetails getData() {
            return mData;
        }
    }
    // endregion helper classes --------------------------------------------------------------------

}