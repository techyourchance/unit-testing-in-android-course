package com.techyourchance.unittestingfundamentals.exercise2;

public class StringDuplicator {

    /**
     * @return concatenation of the argument with itself e.g. "hi" -> "hihi"
     */
    public String duplicate(String string) {
        // the bug is triplication instead of duplication
        return string + string;
    }

}
