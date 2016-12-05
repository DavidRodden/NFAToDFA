package sample.state_machine;

import com.sun.deploy.util.StringUtils;
import sample.DFATransitionDictionary;
import sample.target.DFATargetArrow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David on 11/5/2016.
 */
public class DFANode extends FSMNode {
    private final List<DFATargetArrow> targetArrows;
    private final List<NFANode> nfaNodes;
    private List<Integer> values;

    public DFANode(final double x, final double y, final List<NFANode> nfaNodes) {
        super(x, y);
        this.nfaNodes = nfaNodes;
        targetArrows = new ArrayList<>();
        values = nfaNodes.stream().map(NFANode::getValue).collect(Collectors.toList());
        if (nfaNodes.size() == 1 && nfaNodes.get(0).getText().equals("Ø")) setText("Ø");
        else setText("{" + nfaNodes.stream().map(FSMNode::getText).collect(Collectors.joining(",")).toString() + "}");
        if (nfaNodes.stream().filter(n -> n.isAccept()).findAny().isPresent()) toggleAccept();
    }

    public List<Integer> getValues() {
        return values;
    }

    public boolean contains(final List<Integer> values) {
        return this.values.equals(values);
    }

    public DFATransitionDictionary getTransitionDictionary() {
        return new DFATransitionDictionary(nfaNodes);
    }

    public void updateConnection(final List<DFANode> dfaNodes) {
        targetArrows.clear();
        final List<String> lexicon = new ArrayList<>();
        dfaNodes.forEach(n -> n.targetArrows.forEach(a -> lexicon.addAll(a.getTransitionWords())));
        final List<String> alphabet = lexicon.stream().distinct().collect(Collectors.toList());
        getTransitionDictionary().getTransitionEntries().forEach(e -> {
            if (alphabet.contains(e.getTransitionWord())) alphabet.remove(e.getTransitionWord());
            for (DFANode node : dfaNodes) {
                if (node.values.equals(e.getValues())) {
                    if (!targetArrows.stream().filter(a -> a.getTarget().equals(node)).findAny().isPresent()) {
                        DFATargetArrow dfaTargetArrow = new DFATargetArrow(this, node, e.getTransitionWord());
                        targetArrows.add(dfaTargetArrow);   //will have to add with word attached
                        getChildren().add(dfaTargetArrow.getArrow());
                        getChildren().add(dfaTargetArrow.getLabel());
                    } else
                        targetArrows.stream().filter(a -> a.getTarget().equals(node)).findAny().get().addWord(e.getTransitionWord());
                }
            }
        });
        if (!alphabet.isEmpty()) {
            DFATargetArrow dfaTargetArrow = new DFATargetArrow(this, dfaNodes.get(0), StringUtils.join(alphabet, ", "));
            targetArrows.add(dfaTargetArrow);
            getChildren().add(dfaTargetArrow.getArrow());
            getChildren().add(dfaTargetArrow.getLabel());
        }
    }
}
