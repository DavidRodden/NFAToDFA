package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Pane nfaPane;

    @FXML
    private ImageView nfaImage;

    @FXML
    private Pane dfaPane;

    @FXML
    private Circle dfaPlacement;

    @FXML
    private Circle circlenator;

    @FXML
    private ImageView dfaImage;
    private ObservableList<NFANode> nfaNodes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nfaImage.setImage(new Image(getClass().getResourceAsStream("/img/NFA.png")));
        dfaImage.setImage(new Image(getClass().getResourceAsStream("/img/DFA.png")));
        nfaNodes = FXCollections.observableArrayList();
        nfaNodes.add(new NFANode());
        dfaPlacement.setVisible(false);
        circlenator.setVisible(false);
        nfaPane.setOnMousePressed(event -> {
            if (!event.getButton().equals(MouseButton.PRIMARY)) return;
            for (final NFANode node : nfaNodes)
                if (node.isHover())
                    return;
            final NFANode nfaNode = new NFANode(nfaNodes.size() - 1, event.getX(), event.getY());
            nfaPane.getChildren().add(nfaNode);
            nfaNodes.add(nfaNode);
            updateDFAPane();
            Delta dragDelta = new Delta();
            nfaNode.setOnMousePressed(event1 -> {
                final Point2D mouseInParent = nfaNode.localToParent(event1.getX(), event1.getY());
                dragDelta.setDragDeltaX(nfaNode.getLayoutX() - mouseInParent.getX());
                dragDelta.setDragDeltaY(nfaNode.getLayoutY() - mouseInParent.getY());
                event1.setDragDetect(true);
                event1.consume();
            });
            nfaNode.setOnMouseReleased(event1 -> {
                if (!nfaNode.isDelete()) {
                    nfaNode.setDelete(true);
                    return;
                }
                nfaNodes.remove(nfaNode);
                nfaPane.getChildren().remove(nfaNode);
                for (int i = 1; i < nfaNodes.size(); i++)
                    nfaNodes.get(i).renumber(i);
                updateDFAPane();
            });

            nfaNode.setOnDragDetected(ev -> {
                if (!ev.isShiftDown()) return;
                Dragboard db = nfaNode.startDragAndDrop(TransferMode.LINK);
                ClipboardContent content = new ClipboardContent();
                content.putString(new Integer(nfaNodes.indexOf(nfaNode)).toString());
                db.setContent(content);
            });

            nfaNode.setOnDragOver(e -> {
                // Only accept different nodes than the source
                if (e.getGestureSource() != nfaNode) {
                    String content = e.getDragboard().getContent(DataFormat.PLAIN_TEXT).toString();
                    NFANode draggedCircle = nfaNodes.get(Integer.parseInt(content));
                    System.out.println(draggedCircle.getText().getText() + " -> " + nfaNode.getText().getText());
                    e.acceptTransferModes(TransferMode.ANY);
                }
            });

            // Drag finished on a node
            nfaNode.setOnDragDropped(e -> {
                String content = e.getDragboard().getContent(DataFormat.PLAIN_TEXT).toString();
                NFANode draggedCircle = nfaNodes.get(Integer.parseInt(content));
                System.out.println("Drag completed over circle: " + nfaNode.getBoundsInParent());
                System.out.println("Circle dragged: " + draggedCircle.getBoundsInParent());
                draggedCircle.setTarget(nfaNode);
                e.acceptTransferModes(TransferMode.ANY);
            });
            nfaNode.setOnMouseDragged(event1 -> {
                circlenator.setLayoutX(event1.getSceneX() - nfaNode.getBubble().getRadiusX());
                circlenator.setLayoutY(event1.getSceneY() - 3 * nfaNode.getBubble().getRadiusY());
                nfaNodes.forEach(n -> n.correctArrows(event1.getX(), event1.getY()));
                nfaNode.setDelete(false);
                nfaNode.toFront();
                final Point2D mouseInParent = nfaNode.localToParent(event1.getX(), event1.getY());
                nfaNode.setLayoutX(mouseInParent.getX() + dragDelta.getDragDeltaX());
                nfaNode.setLayoutY(mouseInParent.getY() + dragDelta.getDragDeltaY());
            });
        });

    }

    private void updateDFAPane() {
        dfaPlacement.setRadius(Math.log(nfaNodes.size()) * 75);
        dfaPane.getChildren().removeIf(node -> node instanceof NFANode);
        final double radius = dfaPlacement.getRadius(), centerX = dfaPlacement.getLayoutX(), centerY = dfaPlacement.getLayoutY(), gap = 2 * Math.PI / (nfaNodes.size());
        //dfaPane.getChildren().add(new NFANode(centerX + radius * Math.cos(-Math.PI / 2), centerY + radius * Math.sin(-Math.PI / 2)));
        for (int i = 0; i < nfaNodes.size(); i++) {
            dfaPane.getChildren().add(new NFANode(nfaNodes.get(i).getValue(), centerX + radius * Math.cos(-(i) * gap - Math.PI / 2), centerY + radius * Math.sin(-(i) * gap - Math.PI / 2)));
        }
    }
}