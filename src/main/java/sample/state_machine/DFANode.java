package sample.state_machine;

import javafx.scene.Node;
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
        System.out.println(values);
       // nfaNodes.forEach(n -> values.addAll(n.getTargetValues()));
        //System.out.println("VALUES(1): " + values);
        //values = values.stream().distinct().collect(Collectors.toList());
        //System.out.println("VALUES(2): " + values);
        //final List<String> valueSet = new ArrayList<>();
        //values.forEach(v -> valueSet.add("Q" + (char) ((char) v.intValue() + 8272)));
        if (nfaNodes.size() == 1 && nfaNodes.get(0).getText().equals("Ø")) setText("Ø");
        else setText("{" + nfaNodes.stream().map(FSMNode::getText).collect(Collectors.joining(",")).toString() + "}");
        System.out.println(getText() + " has values " + values);
    }
    public boolean contains(final int value){
        return values.contains(value);
    }
    public void updateConnection(final List<DFANode> dfaNodes) {
        targetArrows.clear();
        for (int i = 0; i < nfaNodes.size(); i++) {
            System.out.println(nfaNodes.get(i).getText() + " has target values: " + nfaNodes.get(i).getTargetValues());
            nfaNodes.get(i).getTargetValues().forEach(v -> {
                for (DFANode node : dfaNodes) {
                    if (node != this && node.contains(v)) {
                        System.out.println("Connected to " + node.getText());
                        targetArrows.add(new DFATargetArrow(this, node));
                    }else System.out.println(node.getText() + " doesn't contain " + v);
                }
            });
        }
        targetArrows.forEach(a -> getChildren().add(a.getArrow()));
        System.out.println(getText() + " connects to " + targetArrows.stream().map(a -> a.getTarget().getText()).collect(Collectors.toList()));
    }
}
