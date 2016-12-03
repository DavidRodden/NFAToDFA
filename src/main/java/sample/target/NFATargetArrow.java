package sample.target;

import sample.state_machine.NFANode;

/**
 * Created by David on 12/2/2016.
 */
public class NFATargetArrow extends TargetArrow {
    public static final int THICK_IDLE = 3, THICK_OVER = 6;
    private NFANode current, target;

    public NFATargetArrow(final NFANode current, final NFANode target) {
        super(current, target, THICK_IDLE, THICK_OVER);
        this.target = target;
    }

    public NFANode getTarget() {
        return target;
    }
}
