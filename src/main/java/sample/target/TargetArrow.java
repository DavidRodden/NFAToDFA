package sample.target;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.state_machine.FSMNode;

/**
 * Created by David on 11/28/2016.
 */
public abstract class TargetArrow {
    private Line arrow;
    private Label label;

    protected TargetArrow(final FSMNode current, final FSMNode target, final int thickIdle, final int thickOver, final String transitionWord) {
        arrow = new Line();
        arrow.setFill(Color.TRANSPARENT);
        arrow.setStroke(Color.BLACK);
        arrow.setStyle("-fx-stroke-width: " + thickIdle);
        final double centerX = current.getBubble().getCenterX(), centerY = current.getBubble().getCenterY();
        arrow.setStartX(centerX);
        arrow.setStartY(centerY);
        arrow.setEndX(target.getBubble().getCenterX());
        arrow.setEndY(target.getBubble().getCenterY());
//        arrow.setControlX((centerX + target.getBubble().getCenterX()) / 2);
//        arrow.setControlY((centerY + target.getBubble().getCenterY()) / 2);
        arrow.setOnMouseEntered(event -> {
            arrow.setStyle("-fx-stroke-width: " + thickOver);
            label.setStyle("-fx-text-fill: red");
        });
        arrow.setOnMouseExited(event -> {
            arrow.setStyle("-fx-stroke-width: " + thickIdle);
            label.setStyle("-fx-text-fill: black");
        });
        arrow.setVisible(true);
        label = new Label(transitionWord);
        label.setLayoutX((arrow.getStartX() + arrow.getEndX()) / 2);
        label.setLayoutY((arrow.getStartY() + arrow.getEndY()) / 2);
        arrow.toBack();
    }

    public Line getArrow() {
        return arrow;
    }

    public void correctArrow(double currentX, double currentY, double targetX, double targetY, boolean draggingCurrentNode) {
        arrow.setEndX(targetX);
        arrow.setEndY(targetY);
        final double arrowAddedLengthX = arrow.getStartX() + arrow.getEndX();
        final double arrowAddedLengthY = arrow.getStartY() + arrow.getEndY();
        label.setLayoutX(arrowAddedLengthX / 2);
        label.setLayoutY(arrowAddedLengthY / 2);
        arrow.toBack();
    }

    public void setArrowBack() {
        arrow.toBack();
    }

    public Label getLabel() {
        return label;
    }
}
