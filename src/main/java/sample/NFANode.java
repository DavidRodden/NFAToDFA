package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by David on 11/5/2016.
 */
public class NFANode extends Group {
    //private List<NFANode> next;
    public static final Font nfaFont = Font.font("Footlight MT Light", 25);
    public static boolean shiftPressed;
    public static NFANode focused;
    private final Ellipse bubble;
    private final Text text;
    private Line arrow;
    private double textWidth;
    private boolean delete;
    private int value;

    public NFANode(int value, double x, double y) {
        this.value = value;
        arrow = new Line();
        arrow.setStyle("-fx-stroke-width: 3");
        arrow.setVisible(false);
        delete = true;
        this.text = new Text(x, y + 5, "");
        if (value >= 0) renumber(value);
        else this.text.setText("Ø");
        this.text.setFont(nfaFont);
        textWidth = this.text.getBoundsInLocal().getWidth();
        bubble = new Ellipse(x, y, this.text.getBoundsInLocal().getWidth() + 5, 25);
        this.text.setX(x - textWidth / 2);
        bubble.setStroke(Color.BLACK);
        bubble.setFill(Color.WHITE);
        getChildren().addAll(bubble, this.text, arrow);
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

    public Line getArrow() {
        return arrow;
    }

    //    public DFANode asDFANode(){
//        return new DFANode()
//    }
    public void setArrowBounds(final double startX, final double startY, final double endX, final double endY) {
        arrow.setStartX(startX);
        arrow.setStartY(startY);
        arrow.setEndX(endX);
        arrow.setEndY(endY);
    }
}
