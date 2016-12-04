package sample.target;

import sample.state_machine.NFANode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David on 12/2/2016.
 */
public class NFATargetArrow extends TargetArrow {
    public static final int THICK_IDLE = 3, THICK_OVER = 6;
    private NFANode current, target;
    private List<String> transitionWords;

    public NFATargetArrow(final NFANode current, final NFANode target, final String transitionWord) {
        super(current, target, THICK_IDLE, THICK_OVER, transitionWord);
        this.target = target;
        transitionWords = Arrays.asList(transitionWord.split(",")).stream().map(String::trim).collect(Collectors.toList());
    }

    public NFANode getTarget() {
        return target;
    }

    public List<String> getTransitionWords() {
        return transitionWords;
    }
}
