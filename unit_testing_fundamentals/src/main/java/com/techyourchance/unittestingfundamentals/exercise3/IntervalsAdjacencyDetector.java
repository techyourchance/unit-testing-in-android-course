package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

public class IntervalsAdjacencyDetector {

    /**
     * @return true if the intervals are adjacent, but don't overlap
     */
    public boolean isAdjacent(Interval interval1, Interval interval2) {
        return (interval1.getEnd() == interval2.getStart() || interval1.getStart() == interval2.getEnd())
                && !isSameIntervals(interval1, interval2);
    }

    private boolean isSameIntervals(Interval interval1, Interval interval2) {
        return interval1.getStart() == interval2.getStart() && interval1.getEnd() == interval2.getEnd();
    }

}
