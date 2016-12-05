package sample;

import com.sun.deploy.util.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.paukov.combinatorics3.Generator;
import sample.state_machine.DFANode;
import sample.state_machine.NFANode;
import sample.target.DFATargetArrow;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    @FXML
    private Pane nfaPane;

    @FXML
    private ImageView nfaImage;

    @FXML
    private TextField transitionWord;

    @FXML
    private Pane dfaPane;

    @FXML
    private Circle dfaPlacement;

    @FXML
    private Circle circlenator;
    @FXML
    private Button snapshotButton;

    @FXML
    private Text alphabet;

    @FXML
    private ImageView dfaImage;
    private ObservableList<NFANode> nfaNodes;
    private ClipboardContent content;

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
            final Delta dragDelta = new Delta();
            nfaNode.setOnMousePressed(event1 -> {
                if (event1.isControlDown()) {
                    nfaNode.toggleAccept();
                    updateDFAPane();
                    event1.consume();
                    return;
                }
                final Point2D mouseInParent = nfaNode.localToParent(event1.getX(), event1.getY());
                dragDelta.setDragDeltaX(nfaNode.getLayoutX() - mouseInParent.getX());
                dragDelta.setDragDeltaY(nfaNode.getLayoutY() - mouseInParent.getY());
                event1.setDragDetect(true);
                event1.consume();
            });
            nfaNode.setOnMouseReleased(event1 -> {
                if (event1.isControlDown()) return;
                if (!nfaNode.isDelete()) {
                    nfaNode.setDelete(true);
                    return;
                }
                nfaNodes.remove(nfaNode);
                for (NFANode node : nfaNodes)
                    node.removeArrows(node.getTargetArrows().stream().filter(a -> a.getTarget().equals(nfaNode)).collect(Collectors.toList()));
                nfaPane.getChildren().remove(nfaNode);
                for (int i = 1; i < nfaNodes.size(); i++)
                    nfaNodes.get(i).renumber(i - 1);
                updateDFAPane();
            });

            nfaNode.setOnDragDetected(ev -> {
                if (!ev.isShiftDown() || ev.isControlDown()) return;
                final Dragboard db = nfaNode.startDragAndDrop(TransferMode.LINK);
                final ClipboardContent content = new ClipboardContent();
                content.putString(new Integer(nfaNodes.indexOf(nfaNode)).toString());
                db.setContent(content);
            });

            nfaNode.setOnDragOver(e -> {
                // Only accept different nodes than the source
                if (e.getGestureSource() != nfaNode) {
                    String content = e.getDragboard().getContent(DataFormat.PLAIN_TEXT).toString();
                    NFANode draggedCircle = nfaNodes.get(Integer.parseInt(content));
                    e.acceptTransferModes(TransferMode.ANY);
                }
            });

            // Drag finished on a node
            nfaNode.setOnDragDropped(e -> {
                String content = e.getDragboard().getContent(DataFormat.PLAIN_TEXT).toString();
                NFANode draggedCircle = nfaNodes.get(Integer.parseInt(content));
                draggedCircle.setTarget(this, nfaNode, transitionWord.getText());
                e.acceptTransferModes(TransferMode.ANY);
                nfaNodes.forEach(n -> n.correctArrows(e.getX(), e.getY(), nfaNode));
                updateDFAPane();
            });
            nfaNode.setOnMouseDragged(event1 -> {
                if (event1.isControlDown()) return;
                circlenator.setLayoutX(event1.getSceneX() - nfaNode.getBubble().getRadiusX());
                circlenator.setLayoutY(event1.getSceneY() - 3 * nfaNode.getBubble().getRadiusY());
                nfaNodes.forEach(n -> n.correctArrows(event1.getX(), event1.getY(), nfaNode));
                nfaNode.setDelete(false);
                nfaNode.toFront();
                final Point2D mouseInParent = nfaNode.localToParent(event1.getX(), event1.getY());
                nfaNode.setLayoutX(mouseInParent.getX() + dragDelta.getDragDeltaX());
                nfaNode.setLayoutY(mouseInParent.getY() + dragDelta.getDragDeltaY());
            });
            updateDFAPane();
        });
        snapshotButton.setOnMouseClicked(event -> {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(dfaPane.snapshot(new SnapshotParameters(), null), null), "png", new File("pic.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateDFAPane() {
        dfaPlacement.setRadius(Math.log(nfaNodes.size()) * 140);
        dfaPane.getChildren().removeIf(node -> node instanceof DFANode);
        final List<List<NFANode>> nfaCombinations = Generator.subset(nfaNodes.stream().filter(n -> !n.getText().equals("Ø")).collect(Collectors.toList())).simple().stream().collect(Collectors.toList());
        final double radius = dfaPlacement.getRadius(), centerX = dfaPlacement.getLayoutX(), centerY = dfaPlacement.getLayoutY(), gap = 2 * Math.PI / (nfaCombinations.size());
        //dfaPane.getChildren().add(new NFANode(centerX + radius * Math.cos(-Math.PI / 2), centerY + radius * Math.sin(-Math.PI / 2)));
        final List<DFANode> dfaNodes = new ArrayList<>();
        for (int i = 0; i < nfaCombinations.size(); i++)
            dfaNodes.add(new DFANode(centerX + radius * Math.cos(-i * gap - Math.PI / 2), centerY + radius * Math.sin(-i * gap - Math.PI / 2), nfaCombinations.get(i)));
        dfaNodes.forEach(n -> n.updateConnection(dfaNodes));
        dfaNodes.get(0).setText("Ø");
        dfaPane.getChildren().addAll(dfaNodes);
        final List<String> lexicon = new ArrayList<>();
        dfaNodes.forEach(n -> n.getTargetArrows().forEach(a -> lexicon.addAll(a.getTransitionWords())));
        final String currentAlphabet = StringUtils.join(lexicon.stream().filter(s -> !s.isEmpty()).distinct().collect(Collectors.toList()), ",");
        alphabet.setText("Alphabet: " + (currentAlphabet.isEmpty() ? "N/A" : currentAlphabet));

    }
}
