package sample.state_machine;

import sample.target.NFATargetArrow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David on 11/5/2016.
 */
public class NFANode extends FSMNode {
    private List<NFATargetArrow> targetArrows;
    private int value;

    public NFANode(int value, double x, double y) {
        super(x, y);
        this.value = value;
        if (value >= 0) renumber(value);
        else setText("Ø");
        targetArrows = new ArrayList<>();
    }

    public NFANode() {
        this(-1, 0, 0);
        setVisible(false);
    }


    public int getValue() {
        return value;
    }

    public void setTarget(final NFANode target) {
        for (NFATargetArrow targetArrow : targetArrows) if (targetArrow.getTarget().equals(target)) return;
        final NFATargetArrow targetArrow = new NFATargetArrow(this, target);
        targetArrow.getArrow().setOnMousePressed(event -> {
            if (event.isShiftDown()) return;
            getChildren().remove(targetArrow.getArrow());
            targetArrows.remove(targetArrow);
        });
        targetArrows.add(targetArrow);
        getChildren().add(targetArrow.getArrow());
    }

    public void renumber(final int value) {
        this.value = value;
        final StringBuilder builder = new StringBuilder();
        for (char c : String.valueOf(value).toCharArray()) builder.append((char) (c + 8272));
        setText("Q" + builder.toString());
    }

    public List<Integer> getTargetValues() {
        return targetArrows.stream().map(a -> a.getTarget().getValue()).collect(Collectors.toList());
    }

    public void correctArrows(double currentX, double currentY) {
        targetArrows.forEach(targetArrow -> targetArrow.correctArrow(currentX, currentY, 0, 0));
    }
}
