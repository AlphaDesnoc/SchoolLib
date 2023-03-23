package fr.desnoc.gestionnary.ui.panels.includes;


import fr.desnoc.gestionnary.managers.PanelManager;
import fr.desnoc.gestionnary.ui.panel.Panel;
import fr.desnoc.gestionnary.ui.panels.ClassPanel;
import fr.desnoc.gestionnary.ui.panels.HomePanel;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LeftPanel extends Panel {

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        final VBox vBox = new VBox();
        GridPane.setVgrow(vBox, Priority.ALWAYS);
        GridPane.setHgrow(vBox, Priority.ALWAYS);
        vBox.setSpacing(5.0D);

        Button booksButton;
        try {
            booksButton = new ImageButton(this.panelManager.getManager().getFileManager().getImage("book")).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        booksButton.setMinSize(70,70);
        booksButton.setMaxSize(70,70);
        booksButton.setStyle("-fx-background-color: transparent; -fx-font-size: 70px");
        booksButton.setTranslateX(40);
        booksButton.setTranslateY(160);
        booksButton.setOnMouseClicked(event -> this.panelManager.showPanel(new HomePanel()));
        booksButton.setOnMouseEntered(event -> booksButton.setCursor(Cursor.HAND));


        Button classButton;
        try {
            classButton = new ImageButton(this.panelManager.getManager().getFileManager().getImage("class")).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        classButton.setMinSize(70,70);
        classButton.setMaxSize(70,70);
        classButton.setStyle("-fx-background-color: transparent; -fx-font-size: 70px");
        classButton.setTranslateX(40);
        classButton.setTranslateY(280);
        classButton.setOnMouseClicked(event -> panelManager.showPanel(new ClassPanel()));
        classButton.setOnMouseEntered(event -> classButton.setCursor(Cursor.HAND));

        vBox.getChildren().addAll(booksButton, classButton);
        Separator separator = new Separator();
        GridPane.setVgrow(separator, Priority.ALWAYS);
        GridPane.setHgrow(separator, Priority.ALWAYS);
        GridPane.setHalignment(separator, HPos.RIGHT);
        separator.setOrientation(Orientation.VERTICAL);
        separator.setTranslateY(1);
        separator.setTranslateX(4);
        separator.setMinWidth(2);
        separator.setMaxWidth(2);
        separator.setOpacity(0.30);
        this.layout.getChildren().add(vBox);
        this.layout.getChildren().add(separator);

    }

}
