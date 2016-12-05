package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by David on 12/4/2016.
 */
public class DFATransitionEntry {
    private final String transitionWord;
    private final List<Integer> values;

    public DFATransitionEntry(String transitionWord, final int value) {
        this.transitionWord = transitionWord;
        values = new ArrayList<>();
        values.add(value);
    }

    public void add(final int value) {
        if (!values.contains(value))
            values.add(value);
    }

    public String getTransitionWord() {
        return transitionWord;
    }

    public List<Integer> getValues() {
        return values;
    }
}
