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
    private Ellipse bubble;
    private final Text text;
    private boolean delete;
    final double x, y;

    protected FSMNode(final double x, final double y) {
        this.x = x;
        this.y = y;
        delete = true;
        this.text = new Text(x, y + 5, "");
        this.text.setFont(fsmFont);
        bubble = new Ellipse(x, y, 0, 25);
        bubble.setStroke(Color.BLACK);
        bubble.setFill(Color.WHITE);
        getChildren().addAll(bubble, this.text);
    }


    public Text getTextLabel() {
        return text;
    }

    public String getText() {
        return text.getText();
    }

    public void setText(final String text) {
        this.text.setText(text);
        final double textWidth = this.text.getBoundsInLocal().getWidth();
        bubble.setRadiusX(this.text.getBoundsInLocal().getWidth()/2 + 15);
        this.text.setX(x - textWidth / 2);
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
