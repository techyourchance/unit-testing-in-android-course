package com.techyourchance.unittestingfundamentals.exercise2;

public class StringDuplicator {

    /**
     * @return concatenation of the argument with itself e.g. "hi" -> "hihi"
     */
    public String duplicate(String string) {
        if (string.length() <= 1) {
            return string;
        } else {
            // the bug is triplication instead of duplication
            return string + string + string;
        }
    }

}
