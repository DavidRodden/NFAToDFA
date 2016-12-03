//package sample.state_machine;
//
//import javafx.scene.Group;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Ellipse;
//import javafx.scene.shape.QuadCurve;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by David on 11/5/2016.
// */
//public class DFANode extends Group {
//    //private List<NFANode> next;
//    public static final Font dfaFont = Font.font("Footlight MT Light", 25);
//    private final Ellipse bubble;
//    private final Text text;
//    private final List<QuadCurve> arrows;
//    private boolean delete;
//    private NFANode nfaNode;
//
//    public DFANode(double x, double y, NFANode nfaNode) {
//        this.nfaNode = nfaNode;
//        arrows = new ArrayList<>();
//        arrows.setStyle("-fx-stroke-width: 3");
//        arrows.setVisible(false);
//        delete = true;
//        this.text = new Text(x, y + 5, "");
//        if (value >= 0) renumber(value);
//        else this.text.setText("Ø");
//        this.text.setFont(dfaFont);
//        final double textWidth = this.text.getBoundsInLocal().getWidth();
//        bubble = new Ellipse(x, y, this.text.getBoundsInLocal().getWidth() + 5, 25);
//        this.text.setX(x - textWidth / 2);
//        bubble.setStroke(Color.BLACK);
//        bubble.setFill(Color.WHITE);
//        getChildren().addAll(bubble, this.text);
//    }
//
//    public void addConnection(final List<DFANode> dfaNodes) {
//        for (nfaNode.)
//            final QuadCurve arrow = new QuadCurve();
//
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public Text getText() {
//        return text;
//    }
//
//    public Ellipse getBubble() {
//        return bubble;
//    }
//
//    public void renumber(final int value) {
//        this.value = value;
//        final StringBuilder builder = new StringBuilder();
//        for (char c : String.valueOf(value).toCharArray()) builder.append((char) (c + 8272));
//        text.setText("Q" + builder.toString());
//    }
//
//    public boolean isDelete() {
//        return delete;
//    }
//
//    public void setDelete(boolean delete) {
//        this.delete = delete;
//    }
//
//    public QuadCurve getArrow() {
//        return arrows;
//    }
//
//    //    public DFANode asDFANode(){
////        return new DFANode()
////    }
//    public void setArrowBounds(final double startX, final double startY, final double endX, final double endY) {
//        arrows.setStartX(startX);
//        arrows.setStartY(startY);
//        arrows.setEndX(endX);
//        arrows.setEndY(endY);
//    }
//}
