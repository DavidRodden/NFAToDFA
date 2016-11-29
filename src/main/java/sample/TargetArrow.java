package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

/**
 * Created by David on 11/28/2016.
 */
public class TargetArrow {
    private NFANode current, target;
    private QuadCurve arrow;

    public TargetArrow(NFANode current, NFANode target) {
        this.target = target;
        arrow = new QuadCurve();
        arrow.setFill(Color.TRANSPARENT);
        arrow.setStroke(Color.BLACK);
        arrow.setStyle("-fx-stroke-width: 5");
        final double centerX = current.getBubble().getCenterX(), centerY = current.getBubble().getCenterY();
        arrow.setStartX(centerX);
        arrow.setStartY(centerY);
        arrow.setEndX(target.getBubble().getCenterX());
        arrow.setEndY(target.getBubble().getCenterY());
        arrow.setControlX((centerX + target.getBubble().getCenterX()) / 2);
        arrow.setControlY((centerY + target.getBubble().getCenterY()) / 2);
        arrow.setOnMouseEntered(event -> arrow.setStyle("-fx-stroke-width: 10"));
        arrow.setOnMouseExited(event -> arrow.setStyle("-fx-stroke-width:5"));
        arrow.setVisible(true);
    }

    public NFANode getTarget() {
        return target;
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
