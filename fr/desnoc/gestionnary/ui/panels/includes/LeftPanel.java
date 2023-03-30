package fr.desnoc.gestionnary.ui.panels.includes;


import fr.desnoc.gestionnary.managers.PanelManager;
import fr.desnoc.gestionnary.ui.panel.Panel;
import fr.desnoc.gestionnary.ui.panels.ClassPanel;
import fr.desnoc.gestionnary.ui.panels.HomePanel;
import fr.desnoc.gestionnary.ui.panels.StudentPanel;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.tools.Tool;
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
        booksButton.setTranslateY(80);
        booksButton.setOnMouseClicked(event -> this.panelManager.showPanel(new HomePanel()));
        booksButton.setOnMouseEntered(event -> booksButton.setCursor(Cursor.HAND));
        Tooltip bookTooltip = new Tooltip("Livres");
        bookTooltip.setFont(new Font(bookTooltip.getFont().toString(), 16));
        booksButton.setTooltip(bookTooltip);


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
        classButton.setTranslateY(200);
        classButton.setOnMouseClicked(event -> panelManager.showPanel(new ClassPanel()));
        classButton.setOnMouseEntered(event -> classButton.setCursor(Cursor.HAND));
        Tooltip classTooltip = new Tooltip("Classes");
        classTooltip.setFont(new Font(classTooltip.getFont().toString(), 16));
        classButton.setTooltip(classTooltip);

        Button studentButton;
        try {
            studentButton = new ImageButton(this.panelManager.getManager().getFileManager().getImage("student")).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        studentButton.setMinSize(70,70);
        studentButton.setMaxSize(70,70);
        studentButton.setStyle("-fx-background-color: transparent; -fx-font-size: 70px");
        studentButton.setTranslateX(40);
        studentButton.setTranslateY(320);
        studentButton.setOnMouseClicked(event -> panelManager.showPanel(new StudentPanel()));
        studentButton.setOnMouseEntered(event -> studentButton.setCursor(Cursor.HAND));
        Tooltip studentTooltip = new Tooltip("Eleves");
        studentTooltip.setFont(new Font(studentTooltip.getFont().toString(), 16));
        studentButton.setTooltip(studentTooltip);


        vBox.getChildren().addAll(booksButton, classButton, studentButton);
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
