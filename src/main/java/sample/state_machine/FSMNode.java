package sample.state_machine;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by David on 12/2/2016.
 */
public class FSMNode extends Group {
    public static final Font fsmFont = Font.font("Footlight MT Light", 25);
    private final Ellipse bubble;
    private final Text text;
    private boolean delete;
    private int value;

    protected FSMNode(final int value, final double x, final double y) {
        this.value = value;
        delete = true;
        this.text = new Text(x, y + 5, "");
        if (value >= 0) renumber(value);
        else this.text.setText("Ø");
        this.text.setFont(fsmFont);
        final double textWidth = this.text.getBoundsInLocal().getWidth();
        bubble = new Ellipse(x, y, this.text.getBoundsInLocal().getWidth() + 5, 25);
        this.text.setX(x - textWidth / 2);
        bubble.setStroke(Color.BLACK);
        bubble.setFill(Color.WHITE);
        getChildren().addAll(bubble, this.text);
    }

    public void renumber(final int value) {
        this.value = value;
        final StringBuilder builder = new StringBuilder();
        for (char c : String.valueOf(value).toCharArray()) builder.append((char) (c + 8272));
        text.setText("Q" + builder.toString());
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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
