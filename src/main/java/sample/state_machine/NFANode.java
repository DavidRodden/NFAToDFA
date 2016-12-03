package sample.state_machine;

import sample.target.NFATargetArrow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 11/5/2016.
 */
public class NFANode extends FSMNode {
    private List<NFATargetArrow> targetArrows;

    public NFANode(int value, double x, double y) {
        super(value, x, y);
        targetArrows = new ArrayList<>();
    }

    public NFANode() {
        this(-1, 0, 0);
        setVisible(false);
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

    public void correctArrows(double currentX, double currentY) {
        targetArrows.forEach(targetArrow -> targetArrow.correctArrow(currentX, currentY, 0, 0));
    }
}
