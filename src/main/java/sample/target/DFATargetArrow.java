package sample.target;

import com.sun.deploy.util.StringUtils;
import sample.state_machine.DFANode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David on 12/2/2016.
 */
public class DFATargetArrow extends TargetArrow {
    public static final int THICK_IDLE = 3, THICK_OVER = 6;
    private DFANode current, target;
    private List<String> transitionWords;

    public DFATargetArrow(final DFANode current, final DFANode target, final String transitionWord) {
        super(current, target, THICK_IDLE, THICK_OVER, transitionWord);
        this.target = target;
        transitionWords = Arrays.asList(transitionWord.split(",")).stream().map(String::trim).collect(Collectors.toList());
    }

    public void addWord(final String transitionWord) {
        if (transitionWords.contains(transitionWord)) return;
        transitionWords.add(transitionWord);
        getLabel().setText(StringUtils.join(transitionWords, ", "));
    }

    public List<String> getTransitionWords() {
        return transitionWords;
    }

    public DFANode getTarget() {
        return target;
    }
}
