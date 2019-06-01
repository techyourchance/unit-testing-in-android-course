package com.techyourchance.testdoublesfundamentals.example6;

public class Counter {

    private static Counter sInstance;

    private int mTotalCount;

    private Counter() {}

    /**
     * @return reference to Counter Singleton
     */
    public static Counter getInstance() {
        if (sInstance == null) {
            sInstance = new Counter();
        }
        return sInstance;
    }

    public void add() {
        mTotalCount++;
    }

    public void add(int count) {
        mTotalCount += count;
    }

    public int getTotal() {
        return mTotalCount;
    }
}
