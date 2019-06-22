package com.techyourchance.unittesting.questions;

import com.techyourchance.unittesting.common.BaseObservable;
import com.techyourchance.unittesting.common.time.TimeProvider;
import com.techyourchance.unittesting.networking.questions.FetchQuestionDetailsEndpoint;
import com.techyourchance.unittesting.networking.questions.QuestionSchema;

import java.util.HashMap;
import java.util.Map;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    public interface Listener {
        void onQuestionDetailsFetched(QuestionDetails questionDetails);
        void onQuestionDetailsFetchFailed();
    }

    private static final long CACHE_TIMEOUT_MS = 60000;

    private final FetchQuestionDetailsEndpoint mFetchQuestionDetailsEndpoint;
    private final TimeProvider mTimeProvider;

    private final Map<String, QuestionDetailsCacheEntry> mQuestionDetailsCache = new HashMap<>();

    public FetchQuestionDetailsUseCase(FetchQuestionDetailsEndpoint fetchQuestionDetailsEndpoint,
                                       TimeProvider timeProvider) {
        mFetchQuestionDetailsEndpoint = fetchQuestionDetailsEndpoint;
        mTimeProvider = timeProvider;
    }

    public void fetchQuestionDetailsAndNotify(final String questionId) {
        if (serveQuestionDetailsFromCacheIfValid(questionId)) {
            return;
        }
        mFetchQuestionDetailsEndpoint.fetchQuestionDetails(questionId, new FetchQuestionDetailsEndpoint.Listener() {
            @Override
            public void onQuestionDetailsFetched(QuestionSchema question) {
                QuestionDetailsCacheEntry cacheEntry = new QuestionDetailsCacheEntry(
                        schemaToQuestionDetails(question),
                        mTimeProvider.getCurrentTimestamp()
                );
                mQuestionDetailsCache.put(questionId, cacheEntry);
                notifySuccess(cacheEntry.mQuestionDetails);
            }

            @Override
            public void onQuestionDetailsFetchFailed() {
                notifyFailure();
            }
        });
    }

    private boolean serveQuestionDetailsFromCacheIfValid(String questionId) {
        final QuestionDetailsCacheEntry cachedQuestionDetailsEntry = mQuestionDetailsCache.get(questionId);
        if (cachedQuestionDetailsEntry != null
                && mTimeProvider.getCurrentTimestamp() < cachedQuestionDetailsEntry.mCachedTimestamp + CACHE_TIMEOUT_MS) {
            notifySuccess(cachedQuestionDetailsEntry.mQuestionDetails);
            return true;
        } else {
            return false;
        }
    }

    private QuestionDetails schemaToQuestionDetails(QuestionSchema questionSchema) {
        return new QuestionDetails(
                questionSchema.getId(),
                questionSchema.getTitle(),
                questionSchema.getBody()
        );
    }

    private void notifyFailure() {
        for (Listener listener : getListeners()) {
            listener.onQuestionDetailsFetchFailed();
        }
    }

    private void notifySuccess(QuestionDetails questionDetails) {
        for (Listener listener : getListeners()) {
            listener.onQuestionDetailsFetched(questionDetails);
        }
    }

    private static class QuestionDetailsCacheEntry {
        private final QuestionDetails mQuestionDetails;
        private final long mCachedTimestamp;

        private QuestionDetailsCacheEntry(QuestionDetails questionDetails, long cachedTimestamp) {
            mQuestionDetails = questionDetails;
            mCachedTimestamp = cachedTimestamp;
        }
    }

}
