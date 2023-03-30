package fr.desnoc.gestionnary.ui.panels;

import fr.desnoc.gestionnary.Main;
import fr.desnoc.gestionnary.managers.PanelManager;
import fr.desnoc.gestionnary.objects.Student;
import fr.desnoc.gestionnary.ui.panel.Panel;
import fr.desnoc.gestionnary.ui.panels.includes.LeftPanel;
import fr.desnoc.gestionnary.ui.panels.includes.StudentRect;
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

public class StudentPanel  extends Panel {

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

        Button addStudent = new Button("Ajouter un Eleve");
        addStudent.setMinSize(200, 30);
        addStudent.setMaxSize(200, 30);
        GridPane.setValignment(addStudent, VPos.CENTER);
        GridPane.setHalignment(addStudent, HPos.CENTER);
        GridPane.setVgrow(addStudent, Priority.ALWAYS);
        GridPane.setHgrow(addStudent, Priority.ALWAYS);
        addStudent.setTranslateY(-45);
        addStudent.setOnAction(e -> panelManager.showAddStudent());


        Button modifyStudent = new Button("Modifier un Eleve");
        modifyStudent.setMinSize(200, 30);
        modifyStudent.setMaxSize(200, 30);
        GridPane.setValignment(modifyStudent, VPos.CENTER);
        GridPane.setHalignment(modifyStudent, HPos.CENTER);
        GridPane.setVgrow(modifyStudent, Priority.ALWAYS);
        GridPane.setHgrow(modifyStudent, Priority.ALWAYS);
        modifyStudent.setOnAction(e -> panelManager.showModifyStudent());

        Button removeStudent = new Button("Retirer un Eleve");
        removeStudent.setMinSize(200, 30);
        removeStudent.setMaxSize(200, 30);
        GridPane.setValignment(removeStudent, VPos.CENTER);
        GridPane.setHalignment(removeStudent, HPos.CENTER);
        GridPane.setVgrow(removeStudent, Priority.ALWAYS);
        GridPane.setHgrow(removeStudent, Priority.ALWAYS);
        removeStudent.setTranslateY(45);
        removeStudent.setOnAction(e -> panelManager.showRemoveStudent());

        Button empruntButton = new Button("Emprunter un livre");
        empruntButton.setMinSize(200, 30);
        empruntButton.setMaxSize(200, 30);
        GridPane.setValignment(empruntButton, VPos.CENTER);
        GridPane.setHalignment(empruntButton, HPos.CENTER);
        GridPane.setVgrow(empruntButton, Priority.ALWAYS);
        GridPane.setHgrow(empruntButton, Priority.ALWAYS);
        empruntButton.setTranslateX(-320);
        empruntButton.setOnAction(e -> this.panelManager.showEmpruntBookStudent());

        Button returnBook = new Button("Retourner un livre");
        returnBook.setMinSize(200, 30);
        returnBook.setMaxSize(200, 30);
        GridPane.setValignment(returnBook, VPos.CENTER);
        GridPane.setHalignment(returnBook, HPos.CENTER);
        GridPane.setVgrow(returnBook, Priority.ALWAYS);
        GridPane.setHgrow(returnBook, Priority.ALWAYS);
        returnBook.setTranslateX(320);
        returnBook.setOnAction(e -> this.panelManager.showReturnBookStudent());

        bottomPanel.getChildren().addAll(separator, addStudent, modifyStudent, removeStudent, empruntButton, returnBook);
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

        List<StudentRect> studentRectArrayList = new ArrayList<>();
        int spaceX = 70;
        int spaceY = 50;
        int t = 0;
        int j = 0;
        int listSize = panelManager.getManager().getStudents().size();
        for(int i = 0; i < listSize; i++){
            Student student = panelManager.getManager().getStudents().get(i);
            StudentRect studentRect = new StudentRect(student);
            this.alwaysElement(studentRect);
            GridPane.setValignment(studentRect, VPos.TOP);
            studentRect.setTranslateX(studentRect.getWidth()*t + spaceX);
            studentRect.setTranslateY(studentRect.getHeight()*j + spaceY);
            studentRectArrayList.add(studentRect);
            //9782848101217
            spaceX += 60;
            t++;
            if(t == 3){
                j++;
                spaceY += 50;
                t = 0;
                spaceX = 80;
            }
            if(listSize%2==0){
                height = (int) (studentRect.getHeight()*(listSize/3) + (60 * listSize/2));
            }else{
                height = (int) (studentRect.getHeight()*((listSize+1)/3) + (60 * (listSize+1)/2));
            }
        }

        VBox vBox = new VBox();
        this.alwaysElement(vBox);
        vBox.setMinHeight(height);
        vBox.setMinWidth(1040);
        vBox.setMaxWidth(1040);
        vBox.setAlignment(Pos.TOP_CENTER);

        centerPane.getChildren().addAll(studentRectArrayList);
        scrollPane.setContent(vBox);
        panel.getChildren().add(scrollPane);
        vBox.getChildren().add(0, centerPane);
    }

    private void alwaysElement(Node element) {
        GridPane.setVgrow(element, Priority.ALWAYS);
        GridPane.setHgrow(element, Priority.ALWAYS);
    }
}
