package sample;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by David on 11/5/2016.
 */
public abstract class TextShape extends StackPane {
    private final Text text;
    private final Shape shape;

    public TextShape(final String text, final double x, final double y) {
        this.text = new Text(x, y, text);
        shape = createShape();
    }

    protected abstract Shape createShape();

    public Text getText() {
        return text;
    }

    public Shape getBackgroundShape() {
        return shape;
    }
}
