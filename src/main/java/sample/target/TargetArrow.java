package sample.target;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.Controller;
import sample.state_machine.FSMNode;

/**
 * Created by David on 11/28/2016.
 */
public abstract class TargetArrow {
    private Line arrow;
    private Circle selfArrow;
    private Label label;
    private boolean self;

    protected TargetArrow(final FSMNode current, final FSMNode target, final int thickIdle, final int thickOver, final String transitionWord) {
        arrow = new Line();
        selfArrow = new Circle();
        arrow.setFill(Color.TRANSPARENT);
        arrow.setStroke(Color.BLACK);
        arrow.setStyle("-fx-stroke-width: " + thickIdle);
        final double centerX = current.getBubble().getCenterX(), centerY = current.getBubble().getCenterY();
        label = new Label(transitionWord);
        label.setStyle("-fx-font-size: 20");
        if (self = current.equals(target)) {
            selfArrow.setRadius(5);
            selfArrow.setLayoutX(target.getBubble().getCenterX() + selfArrow.getRadius());
            selfArrow.setLayoutY(target.getBubble().getCenterY() + selfArrow.getRadius());
            label.setLayoutX(selfArrow.getCenterX());
            label.setLayoutY(selfArrow.getCenterY());
        } else {
            arrow.setStartX(centerX);
            arrow.setStartY(centerY);
            arrow.setEndX(target.getBubble().getCenterX());
            arrow.setEndY(target.getBubble().getCenterY());
            label.setLayoutX((arrow.getStartX() + arrow.getEndX()) / 2);
            label.setLayoutY((arrow.getStartY() + arrow.getEndY()) / 2);
        }
        (self ? selfArrow : arrow).setOnMouseEntered(event -> {
            (self ? selfArrow : arrow).setStyle("-fx-stroke-width: " + thickOver);
            label.setStyle("-fx-text-fill: red; -fx-font-size: 25");
        });
        (self ? selfArrow : arrow).setOnMouseExited(event -> {
            (self ? selfArrow : arrow).setStyle("-fx-stroke-width: " + thickIdle);
            label.setStyle("-fx-text-fill: black; -fx-font-size: 20");
        });
        (self ? selfArrow : arrow).setVisible(true);
        (self ? selfArrow : arrow).toBack();
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
