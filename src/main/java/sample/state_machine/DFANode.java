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
        values = nfaNodes.stream().map(NFANode::getValue).collect(Collectors.toList());
        if (nfaNodes.size() == 1 && nfaNodes.get(0).getText().equals("Ø")) setText("Ø");
        else setText("{" + nfaNodes.stream().map(FSMNode::getText).collect(Collectors.joining(",")).toString() + "}");
    }

    public boolean contains(final List<Integer> values) {
        return this.values.equals(values);
    }

    public void updateConnection(final List<DFANode> dfaNodes) {
        System.out.println("examining node " + getText() + nfaNodes.stream().map(NFANode::getTargetValues).collect(Collectors.toList()));
        targetArrows.clear();
        for (int i = 0; i < nfaNodes.size(); i++) {
            nfaNodes.forEach(n -> {
                for (DFANode node : dfaNodes) {
                    if (!node.values.isEmpty() && node.contains(n.getTargetValues())) {
                        System.out.println("-> connect to " + node.getText());
                        targetArrows.add(new DFATargetArrow(this, node));
                    }
                }
            });
        }
        targetArrows.forEach(a -> getChildren().add(a.getArrow()));
    }
}
