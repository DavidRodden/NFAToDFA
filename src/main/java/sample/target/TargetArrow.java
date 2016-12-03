package sample.target;

import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import sample.state_machine.FSMNode;

/**
 * Created by David on 11/28/2016.
 */
public abstract class TargetArrow {
    private QuadCurve arrow;

    protected TargetArrow(final FSMNode current, final FSMNode target, final int thickIdle, final int thickOver) {
        arrow = new QuadCurve();
        arrow.setFill(Color.TRANSPARENT);
        arrow.setStroke(Color.BLACK);
        arrow.setStyle("-fx-stroke-width: " + thickIdle);
        final double centerX = current.getBubble().getCenterX(), centerY = current.getBubble().getCenterY();
        arrow.setStartX(centerX);
        arrow.setStartY(centerY);
        arrow.setEndX(target.getBubble().getCenterX());
        arrow.setEndY(target.getBubble().getCenterY());
        arrow.setControlX((centerX + target.getBubble().getCenterX()) / 2);
        arrow.setControlY((centerY + target.getBubble().getCenterY()) / 2);
        arrow.setOnMouseEntered(event -> arrow.setStyle("-fx-stroke-width: " + thickOver));
        arrow.setOnMouseExited(event -> arrow.setStyle("-fx-stroke-width: " + thickIdle));
        arrow.setVisible(true);
    }

    public QuadCurve getArrow() {
        return arrow;
    }
    public void correctArrow(double currentX, double currentY, double targetX, double targetY){
        arrow.setEndX(targetX);
        arrow.setEndY(targetY);
        arrow.setControlX((currentX + targetX) / 2);
        arrow.setControlY((currentY + targetY) / 2);
    }
}
