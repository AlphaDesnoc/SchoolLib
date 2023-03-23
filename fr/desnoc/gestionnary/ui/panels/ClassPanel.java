package fr.desnoc.gestionnary.ui.panels;

import fr.desnoc.gestionnary.Main;
import fr.desnoc.gestionnary.managers.PanelManager;
import fr.desnoc.gestionnary.objects.Clazz;
import fr.desnoc.gestionnary.objects.Teacher;
import fr.desnoc.gestionnary.ui.panel.Panel;
import fr.desnoc.gestionnary.ui.panels.includes.ClassRect;
import fr.desnoc.gestionnary.ui.panels.includes.LeftPanel;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class ClassPanel extends Panel {

    private final LeftPanel leftPanel = new LeftPanel();
    private final GridPane topPanel = new GridPane();
    private final GridPane bottomPanel = new GridPane();

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        GridPane centerPanel = new GridPane();
        GridPane.setVgrow(centerPanel, Priority.ALWAYS);
        GridPane.setHgrow(centerPanel, Priority.ALWAYS);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setMinWidth(150);
        columnConstraints.setMaxWidth(150);
        this.layout.getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());
        this.layout.add(leftPanel.getLayout(), 0, 0);
        this.leftPanel.init(this.panelManager);
        this.layout.add(centerPanel,1,0);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.BOTTOM);
        rowConstraints.setMinHeight(500);
        rowConstraints.setMaxHeight(500);
        rowConstraints.setVgrow(Priority.ALWAYS);
        centerPanel.getRowConstraints().addAll(rowConstraints, new RowConstraints());
        centerPanel.add(topPanel, 0, 0);
        centerPanel.add(bottomPanel, 0, 1);

        showCenterPanel(topPanel);

        Separator separator = new Separator();
        GridPane.setValignment(separator, VPos.TOP);
        alwaysElement(separator);
        separator.setTranslateY(1.0D);
        separator.setTranslateX(2.0D);
        separator.setOrientation(Orientation.HORIZONTAL);
        separator.setMinHeight(2.0D);
        separator.setMaxHeight(2.0D);
        separator.setOpacity(0.3D);
        alwaysElement(topPanel);
        alwaysElement(bottomPanel);

        Button addClass = new Button("Ajouter une Classe");
        addClass.setMinSize(200, 30);
        addClass.setMaxSize(200, 30);
        GridPane.setValignment(addClass, VPos.CENTER);
        GridPane.setHalignment(addClass, HPos.CENTER);
        GridPane.setVgrow(addClass, Priority.ALWAYS);
        GridPane.setHgrow(addClass, Priority.ALWAYS);
        addClass.setTranslateY(-45);
        addClass.setOnAction(e -> panelManager.showAddClass());


        Button modifyClass = new Button("Modifier une Classe");
        modifyClass.setMinSize(200, 30);
        modifyClass.setMaxSize(200, 30);
        GridPane.setValignment(modifyClass, VPos.CENTER);
        GridPane.setHalignment(modifyClass, HPos.CENTER);
        GridPane.setVgrow(modifyClass, Priority.ALWAYS);
        GridPane.setHgrow(modifyClass, Priority.ALWAYS);
        modifyClass.setOnAction(e -> panelManager.showModifyClass());

        Button removeClass = new Button("Retirer une Classe");
        removeClass.setMinSize(200, 30);
        removeClass.setMaxSize(200, 30);
        GridPane.setValignment(removeClass, VPos.CENTER);
        GridPane.setHalignment(removeClass, HPos.CENTER);
        GridPane.setVgrow(removeClass, Priority.ALWAYS);
        GridPane.setHgrow(removeClass, Priority.ALWAYS);
        removeClass.setTranslateY(45);
        removeClass.setOnAction(e -> panelManager.showRemoveClass());

        Button empruntButton = new Button("Emprunter un livre");
        empruntButton.setMinSize(200, 30);
        empruntButton.setMaxSize(200, 30);
        GridPane.setValignment(empruntButton, VPos.CENTER);
        GridPane.setHalignment(empruntButton, HPos.CENTER);
        GridPane.setVgrow(empruntButton, Priority.ALWAYS);
        GridPane.setHgrow(empruntButton, Priority.ALWAYS);
        empruntButton.setTranslateX(-320);
        empruntButton.setOnAction(e -> this.panelManager.showEmpruntBook());

        Button returnBook = new Button("Retourner un livre");
        returnBook.setMinSize(200, 30);
        returnBook.setMaxSize(200, 30);
        GridPane.setValignment(returnBook, VPos.CENTER);
        GridPane.setHalignment(returnBook, HPos.CENTER);
        GridPane.setVgrow(returnBook, Priority.ALWAYS);
        GridPane.setHgrow(returnBook, Priority.ALWAYS);
        returnBook.setTranslateX(320);
        returnBook.setOnAction(e -> this.panelManager.showReturnBook());

        bottomPanel.getChildren().addAll(separator, addClass, modifyClass, removeClass, empruntButton, returnBook);
    }

    private void showCenterPanel(GridPane panel){

        ScrollPane scrollPane = new ScrollPane();
        this.alwaysElement(scrollPane);
        scrollPane.getStylesheets().add(Main.class.getResource("/css/scrollbar.css").toExternalForm());

        int height = 50;

        GridPane centerPane = new GridPane();
        alwaysElement(centerPane);
        GridPane.setValignment(centerPane, VPos.BOTTOM);
        centerPane.setMaxWidth(1080);
        centerPane.setMinWidth(1080);

        List<ClassRect> classList = new ArrayList<>();
        int spaceX = 120;
        int spaceY = 50;
        int t = 0;
        int j = 0;
        int listSize = panelManager.getManager().getClasses().size();
        for(int i = 0; i < listSize; i++){
            Clazz clazz = panelManager.getManager().getClasses().get(i);
            ClassRect classRect = new ClassRect(clazz);
            this.alwaysElement(classRect);
            GridPane.setValignment(classRect, VPos.TOP);
            classRect.setTranslateX(classRect.getWidth()*t + spaceX);
            classRect.setTranslateY(classRect.getHeight()*j + spaceY);
            classList.add(classRect);

            spaceX += 120;
            t++;
            if(t == 2){
                j++;
                spaceY += 50;
                t = 0;
                spaceX = 120;
            }
            if(listSize%2==0){
                height = (int) (classRect.getHeight()*(listSize/2) + (60 * listSize/2));
            }else{
                height = (int) (classRect.getHeight()*((listSize+1)/2) + (60 * (listSize+1)/2));
            }
        }

        VBox vBox = new VBox();
        this.alwaysElement(vBox);
        vBox.setMinHeight(height);
        vBox.setMinWidth(1040);
        vBox.setMaxWidth(1040);
        vBox.setAlignment(Pos.TOP_CENTER);

        centerPane.getChildren().addAll(classList);
        scrollPane.setContent(vBox);
        panel.getChildren().add(scrollPane);
        vBox.getChildren().add(0, centerPane);
    }

    private void alwaysElement(Node element) {
        GridPane.setVgrow(element, Priority.ALWAYS);
        GridPane.setHgrow(element, Priority.ALWAYS);
    }
}
