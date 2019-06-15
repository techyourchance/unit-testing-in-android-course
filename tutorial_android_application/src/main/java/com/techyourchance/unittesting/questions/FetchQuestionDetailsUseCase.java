package com.techyourchance.unittesting.questions;

import com.techyourchance.unittesting.common.BaseObservable;
import com.techyourchance.unittesting.networking.questions.FetchQuestionDetailsEndpoint;
import com.techyourchance.unittesting.networking.questions.QuestionSchema;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    public interface Listener {
        void onQuestionDetailsFetched(QuestionDetails questionDetails);
        void onQuestionDetailsFetchFailed();
    }

    private final FetchQuestionDetailsEndpoint mFetchQuestionDetailsEndpoint;

    public FetchQuestionDetailsUseCase(FetchQuestionDetailsEndpoint fetchQuestionDetailsEndpoint) {
        mFetchQuestionDetailsEndpoint = fetchQuestionDetailsEndpoint;
    }

    public void fetchQuestionDetailsAndNotify(String questionId) {
        mFetchQuestionDetailsEndpoint.fetchQuestionDetails(questionId, new FetchQuestionDetailsEndpoint.Listener() {
            @Override
            public void onQuestionDetailsFetched(QuestionSchema question) {
                notifySuccess(question);

            }

            @Override
            public void onQuestionDetailsFetchFailed() {
                notifyFailure();
            }
        });
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onQuestionDetailsFetchFailed();
        }
    }

    private void notifySuccess(QuestionSchema questionSchema) {
        for (Listener listener : getListeners()) {
            listener.onQuestionDetailsFetched(
                    new QuestionDetails(
                            questionSchema.getId(),
                            questionSchema.getTitle(),
                            questionSchema.getBody()
                    ));
        }
    }
}
