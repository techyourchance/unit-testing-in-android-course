package com.techyourchance.unittesting.common.dependencyinjection;

import com.techyourchance.unittesting.common.Constants;
import com.techyourchance.unittesting.common.time.TimeProvider;
import com.techyourchance.unittesting.networking.StackoverflowApi;
import com.techyourchance.unittesting.networking.questions.FetchQuestionDetailsEndpoint;
import com.techyourchance.unittesting.questions.FetchQuestionDetailsUseCase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {

    private Retrofit mRetrofit;
    private FetchQuestionDetailsUseCase mFetchQuestionDetailsUseCase;

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public StackoverflowApi getStackoverflowApi() {
        return getRetrofit().create(StackoverflowApi.class);
    }

    public TimeProvider getTimeProvider() {
        return new TimeProvider();
    }

    private FetchQuestionDetailsEndpoint getFetchQuestionDetailsEndpoint() {
        return new FetchQuestionDetailsEndpoint(getStackoverflowApi());
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        if (mFetchQuestionDetailsUseCase == null) {
            mFetchQuestionDetailsUseCase = new FetchQuestionDetailsUseCase(getFetchQuestionDetailsEndpoint(), getTimeProvider());
        }
        return mFetchQuestionDetailsUseCase;
    }
}
