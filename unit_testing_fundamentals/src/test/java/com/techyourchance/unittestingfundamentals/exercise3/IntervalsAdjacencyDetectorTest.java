package com.techyourchance.unittestingfundamentals.exercise3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntervalsAdjacencyDetectorTest {

    IntervalsAdjacencyDetector SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new IntervalsAdjacencyDetector();
    }

    // один перед другим касается
    @Test
    public void isAdjacent_interval1AdjToInterval2_trueReturned() {
        Interval interval1 = new Interval(-5,2);
        Interval interval2 = new Interval(2,5);
        boolean result = SUT.isAdjacent(interval1,interval2);
        assertTrue(result);
    }

    // один после другого касается
    @Test
    public void isAdjacent_interval2AdjToInterval1_trueReturned() {
        Interval interval1 = new Interval(5,10);
        Interval interval2 = new Interval(-1,5);
        boolean result = SUT.isAdjacent(interval1,interval2);
        assertTrue(result);
    }

    // один перед другим оверлап
    @Test
    public void isAdjacent_interval1OverlapsInterval2_falseReturned() {
        Interval interval1 = new Interval(-5,10);
        Interval interval2 = new Interval(8,15);
        boolean result = SUT.isAdjacent(interval1,interval2);
        assertFalse(result);
    }

    // один после другого оверлап
    @Test
    public void isAdjacent_interval2OverlapsInterval1_falseReturned() {
        Interval interval1 = new Interval(3,20);
        Interval interval2 = new Interval(-5,10);
        boolean result = SUT.isAdjacent(interval1,interval2);
        assertFalse(result);
    }

    // один перед другим на отдалении
    @Test
    public void isAdjacent_interval1BeforeInterval2_falseReturned() {
        Interval interval1 = new Interval(-3,20);
        Interval interval2 = new Interval(22,28);
        boolean result = SUT.isAdjacent(interval1,interval2);
        assertFalse(result);
    }


    // один после другого на отдалении
    @Test
    public void isAdjacent_interval2BeforeInterval1_falseReturned() {
        Interval interval1 = new Interval(-3,20);
        Interval interval2 = new Interval(-10,-5);
        boolean result = SUT.isAdjacent(interval1,interval2);
        assertFalse(result);
    }

    @Test
    public void isAdjacent_sameIntervals_falseReturned() {
        Interval interval1 = new Interval(-3,20);
        Interval interval2 = new Interval(-3,20);
        boolean result = SUT.isAdjacent(interval1,interval2);
        assertFalse(result);
    }


}