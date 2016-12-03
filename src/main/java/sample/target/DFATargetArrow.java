package sample.target;

import sample.state_machine.DFANode;

/**
 * Created by David on 12/2/2016.
 */
public class DFATargetArrow extends TargetArrow {
    private DFANode current, target;

    public DFATargetArrow(final DFANode current, final DFANode target) {
        super(current, target);
        this.target = target;
    }

    public DFANode getTarget() {
        return target;
    }
}
