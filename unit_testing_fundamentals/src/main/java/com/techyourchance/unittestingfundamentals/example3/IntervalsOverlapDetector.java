package com.techyourchance.unittestingfundamentals.example3;

public class IntervalsOverlapDetector {

    public boolean isOverlap(Interval interval1, Interval interval2) {
        return interval1.getEnd() > interval2.getStart() && interval1.getStart() < interval2.getEnd();
    }

}
