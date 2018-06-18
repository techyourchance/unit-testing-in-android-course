package com.techyourchance.unittestinginandroid.example14;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class MyActivity extends Activity {

    private int mCount;

    @Override
    protected void onStart() {
        super.onStart();
        mCount++;
    }

    public int getCount() {
        return mCount;
    }
}
