package sample.target;

import sample.state_machine.DFANode;

/**
 * Created by David on 12/2/2016.
 */
public class DFATargetArrow extends TargetArrow {
    public static final int THICK_IDLE = 2, THICK_OVER = 4;
    private DFANode current, target;

    public DFATargetArrow(final DFANode current, final DFANode target) {
        super(current, target, THICK_IDLE, THICK_OVER, "");
        this.target = target;
    }

    public DFANode getTarget() {
        return target;
    }
}
