package sample.target;

import sample.state_machine.NFANode;

/**
 * Created by David on 12/2/2016.
 */
public class NFATargetArrow extends TargetArrow {
    private NFANode current, target;

    public NFATargetArrow(final NFANode current, final NFANode target) {
        super(current, target);
        this.target = target;
    }

    public NFANode getTarget() {
        return target;
    }
}
