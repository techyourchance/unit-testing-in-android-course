package com.techyourchance.unittestingfundamentals.example3;

public class Interval {

    private final int mStart;
    private final int mEnd;

    public Interval(int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException("invalid interval range");
        }
        mStart = start;
        mEnd = end;
    }

    public int getStart() {
        return mStart;
    }

    public int getEnd() {
        return mEnd;
    }
}
