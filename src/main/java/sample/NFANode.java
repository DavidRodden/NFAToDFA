package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 11/5/2016.
 */
public class NFANode extends Group {
    public static final Font nfaFont = Font.font("Footlight MT Light", 25);

    private List<TargetArrow> targetArrows;

    private final Ellipse bubble;
    private final Text text;
    private boolean delete;
    private int value;

    public NFANode(int value, double x, double y) {
        targetArrows = new ArrayList<>();
        this.value = value;
        delete = true;
        this.text = new Text(x, y + 5, "");
        if (value >= 0) renumber(value);
        else this.text.setText("Ø");
        this.text.setFont(nfaFont);
        final double textWidth = this.text.getBoundsInLocal().getWidth();
        bubble = new Ellipse(x, y, this.text.getBoundsInLocal().getWidth() + 5, 25);
        this.text.setX(x - textWidth / 2);
        bubble.setStroke(Color.BLACK);
        bubble.setFill(Color.WHITE);
        getChildren().addAll(bubble, this.text);

    }

    public NFANode() {
        this(-1, 0, 0);
        setVisible(false);
    }

    public int getValue() {
        return value;
    }

    public Text getText() {
        return text;
    }

    public Ellipse getBubble() {
        return bubble;
    }

    public void setTarget(final NFANode target) {
        for (TargetArrow targetArrow : targetArrows) if (targetArrow.getTarget().equals(target)) return;
        final TargetArrow targetArrow = new TargetArrow(this, target);
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
        text.setText("Q" + builder.toString());
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public void correctArrows(double currentX, double currentY) {

        targetArrows.forEach(targetArrow -> targetArrow.correctArrow(currentX, currentY, 0, 0));
    }
    //    public DFANode asDFANode(){
//        return new DFANode()
//    }

}
