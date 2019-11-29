package com.techyourchance.unittestingfundamentals.exercise3;

import com.techyourchance.unittestingfundamentals.example3.Interval;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class IntervalsAdjacencyDetectorTest {

    private IntervalsAdjacencyDetector SUT;

    @Before
    public void setUp() throws Exception {
        SUT = new IntervalsAdjacencyDetector();
    }

    @Test
    public void isAdjacent_firstBefore_returnFalse() {
        Interval interval1 =  new Interval(1, 3);
        Interval interval2 = new Interval(4, 7);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_firstStartsAfterSecond_returnFalse() {
        Interval interval1 =  new Interval(11, 13);
        Interval interval2 = new Interval(1, 7);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_intervalsStartsAtTheSameTime_returnFalse() {
        Interval interval1 =  new Interval(1, 3);
        Interval interval2 = new Interval(1, 7);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_intervalsStartsAndEndsTheSameTime_returnFalse() {
        Interval interval1 =  new Interval(1, 3);
        Interval interval2 = new Interval(1, 3);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_endsAtTheSameTimeFirstStartsAfter_returnFalse() {
        Interval interval1 =  new Interval(3, 7);
        Interval interval2 = new Interval(1, 7);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_endsAtTheSameTimeFirstStartsBefore_returnFalse() {
        Interval interval1 =  new Interval(2, 7);
        Interval interval2 = new Interval(3, 7);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(false));
    }

    @Test
    public void isAdjacent_firstEndsWhenSecondStaring_returnTrue() {
        Interval interval1 =  new Interval(1, 3);
        Interval interval2 = new Interval(3, 7);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

    @Test
    public void isAdjacent_firstStartsWhenSecondEnds_returnTrue() {
        Interval interval1 =  new Interval(7, 13);
        Interval interval2 = new Interval(1, 7);
        boolean result = SUT.isAdjacent(interval1, interval2);
        Assert.assertThat(result, is(true));
    }

}