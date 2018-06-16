package com.techyourchance.testdrivendevelopment.example11;

import com.techyourchance.testdrivendevelopment.example11.cart.CartItem;
import com.techyourchance.testdrivendevelopment.example11.networking.CartItemSchema;
import com.techyourchance.testdrivendevelopment.example11.networking.GetCartItemsHttpEndpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FetchCartItemsManualTestDoublesUseCaseTest {

    // region constants ----------------------------------------------------------------------------
    public static final int LIMIT = 10;
    public static final int PRICE = 5;
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    public static final String ID = "id";
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------
    GetCartItemsHttpEndpointTd mGetCartItemsHttpEndpointTd;
    @Mock FetchCartItemsUseCase.Listener mListenerMock1;
    @Mock FetchCartItemsUseCase.Listener mListenerMock2;

    @Captor ArgumentCaptor<List<CartItem>> mAcListCartItem;
    // endregion helper fields ---------------------------------------------------------------------

    FetchCartItemsUseCase SUT;

    @Before
    public void setup() throws Exception {
        mGetCartItemsHttpEndpointTd = new GetCartItemsHttpEndpointTd();
        SUT = new FetchCartItemsUseCase(mGetCartItemsHttpEndpointTd);
        success();
    }

    private List<CartItemSchema> getCartItemSchemes() {
        List<CartItemSchema> schemas = new ArrayList<>();
        schemas.add(new CartItemSchema(ID, TITLE, DESCRIPTION, PRICE));
        return schemas;
    }

    @Test
    public void fetchCartItems_correctLimitPassedToEndpoint() throws Exception {
        // Arrange
        // Act
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        assertThat(mGetCartItemsHttpEndpointTd.mInvocationCount, is(1));
        assertThat(mGetCartItemsHttpEndpointTd.mLastLimit, is(LIMIT));
    }

    @Test
    public void fetchCartItems_success_observersNotifiedWithCorrectData() throws Exception {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onCartItemsFetched(mAcListCartItem.capture());
        verify(mListenerMock2).onCartItemsFetched(mAcListCartItem.capture());
        List<List<CartItem>> captures = mAcListCartItem.getAllValues();
        List<CartItem> capture1 = captures.get(0);
        List<CartItem> capture2 = captures.get(1);
        assertThat(capture1, is(getCartItems()));
        assertThat(capture2, is(getCartItems()));
    }

    @Test
    public void fetchCartItems_success_unsubscribedObserversNotNotified() throws Exception {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.unregisterListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onCartItemsFetched(any(List.class));
        verifyNoMoreInteractions(mListenerMock2);
    }

    @Test
    public void fetchCartItems_generalError_observersNotifiedOfFailure() throws Exception {
        // Arrange
        generalError();
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onFetchCartItemsFailed();
        verify(mListenerMock2).onFetchCartItemsFailed();
    }

    @Test
    public void fetchCartItems_networkError_observersNotifiedOfFailure() throws Exception {
        // Arrange
        networkError();
        // Act
        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);
        // Assert
        verify(mListenerMock1).onFetchCartItemsFailed();
        verify(mListenerMock2).onFetchCartItemsFailed();
    }

    // region helper methods -----------------------------------------------------------------------

    private List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(ID, TITLE, DESCRIPTION, PRICE));
        return cartItems;
    }

    private void success() {
        // no-op
    }

    private void networkError() {
        mGetCartItemsHttpEndpointTd.mNetworkError = true;
    }

    private void generalError() {
        mGetCartItemsHttpEndpointTd.mGeneralError = true;
    }

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------

    private class GetCartItemsHttpEndpointTd implements GetCartItemsHttpEndpoint {

        private int mInvocationCount;
        private int mLastLimit;

        private boolean mNetworkError;
        private boolean mGeneralError;

        @Override
        public void getCartItems(int limit, Callback callback) {
            mInvocationCount++;
            mLastLimit = limit;
            if (mNetworkError) {
                callback.onGetCartItemsFailed(FailReason.NETWORK_ERROR);
            } else if (mGeneralError) {
                callback.onGetCartItemsFailed(FailReason.GENERAL_ERROR);
            } else {
                callback.onGetCartItemsSucceeded(getCartItemSchemes());
            }
        }
    }
    // endregion helper classes --------------------------------------------------------------------

}