package sample.state_machine;

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
        values = new ArrayList<>();
        nfaNodes.forEach(n -> values.addAll(n.getTargetValues()));
        values = values.stream().distinct().collect(Collectors.toList());
        final List<String> valueSet = new ArrayList<>();
        values.forEach(v -> valueSet.add("Q" + (char) ((char) v.intValue() + 8272)));
        if (nfaNodes.size() == 1 && nfaNodes.get(0).getText().equals("Ø")) setText("Ø");
        else setText("{" + nfaNodes.stream().map(FSMNode::getText).collect(Collectors.joining(",")).toString() + "}");
    }

    public void updateConnection(final List<DFANode> dfaNodes) {
        //cheeky change
        targetArrows.clear();
        for (int i = 0; i < nfaNodes.size(); i++)
            targetArrows.add(new DFATargetArrow(this, dfaNodes.get(i)));
        targetArrows.forEach(a -> getChildren().add(a.getArrow()));
        System.out.println(getText() + " connects to " + targetArrows);
    }
}
