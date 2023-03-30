package fr.desnoc.gestionnary.managers;

import fr.desnoc.gestionnary.objects.Book;
import fr.desnoc.gestionnary.objects.Clazz;
import fr.desnoc.gestionnary.objects.Student;
import fr.desnoc.gestionnary.objects.Teacher;
import fr.desnoc.gestionnary.ui.panel.IPanel;
import fr.desnoc.gestionnary.ui.panels.ClassPanel;
import fr.desnoc.gestionnary.ui.panels.HomePanel;
import fr.desnoc.gestionnary.ui.panels.StudentPanel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PanelManager {

    private final Manager manager;
    private final Stage stage;
    private final GridPane centerPane = new GridPane();

    private final HomePanel homePanel = new HomePanel();
    public PanelManager(Manager manager, Stage stage){
        this.manager = manager;
        this.stage = stage;
    }

    public void init(){
        this.stage.setTitle("Bibliothèque Marcel Pagnol");
        this.stage.setMinWidth(1280);
        this.stage.setMinHeight(720);
        this.stage.setResizable(true);
        this.stage.centerOnScreen();
        this.stage.setResizable(false);
        this.stage.show();

        GridPane layout = new GridPane();
        layout.setStyle("-fx-background-color: rgb(120,120,120); -fx-background-size: cover");
        this.stage.setScene(new Scene(layout));

        final MenuBar menuBar = new MenuBar();

        final Menu menuFile = new Menu("Fichier");
        final Menu menuEdit = new Menu("Editer");

        Menu menuEditBooks = new Menu("Livres");
        Menu menuEditClass = new Menu("Classes");
        Menu menuEditStudent = new Menu("Eleves");

        MenuItem itemSaveBook = new MenuItem("Sauvegarder les livres");
        MenuItem itemSaveClass = new MenuItem("Sauvegarder les classes");
        MenuItem itemSaveAll = new MenuItem("Tout sauvegarder");
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        MenuItem itemExit = new MenuItem("Quitter");

        MenuItem itemAddClass = new MenuItem("Ajouter une classe");
        MenuItem itemModClass = new MenuItem("Modifier une classe");
        MenuItem itemRemoveClass = new MenuItem("Retirer une classe");
        MenuItem itemEmpruntBookClass = new MenuItem("Emprunter un livre");
        MenuItem itemReturnBookClass = new MenuItem("Rendre un livre");

        MenuItem itemAddStudent = new MenuItem("Ajouter un eleve");
        MenuItem itemModStudent = new MenuItem("Modifier un eleve");
        MenuItem itemRemoveStudent = new MenuItem("Retirer un eleve");
        MenuItem itemEmpruntBookStudent = new MenuItem("Emprunter un livre");
        MenuItem itemReturnBookStudent = new MenuItem("Rendre un livre");

        MenuItem itemRemoveBook = new MenuItem("Retirer un livre");
        MenuItem itemAddBook = new MenuItem("Ajouter un livre");
        MenuItem itemModBook = new MenuItem("Modifier un livre");

        itemSaveBook.setOnAction(event -> {
            try {
                getManager().saveBooks();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        itemSaveClass.setOnAction(event -> {
            try {
                getManager().saveClasses();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        itemSaveAll.setOnAction(event -> {
            try {
                getManager().saveBooks();
                getManager().saveClasses();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        itemExit.setOnAction(event -> {
            try {
                getManager().saveBooks();
                getManager().saveClasses();
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        itemAddBook.setOnAction(event -> showAddBook());
        itemRemoveBook.setOnAction(event -> showRemoveBook());
        itemModBook.setOnAction(event -> showModifyBook());

        itemAddClass.setOnAction(e -> showAddClass());
        itemRemoveClass.setOnAction(e -> showRemoveClass());
        itemModClass.setOnAction(e -> showModifyClass());
        itemEmpruntBookClass.setOnAction(event -> showEmpruntBookClass());
        itemReturnBookClass.setOnAction(event -> showReturnBookClass());

        menuFile.getItems().addAll(itemSaveBook, itemSaveClass, itemSaveAll, separatorMenuItem, itemExit);
        menuEditBooks.getItems().addAll(itemAddBook, itemRemoveBook, itemModBook);
        menuEditClass.getItems().addAll(itemAddClass, itemRemoveClass, itemModClass, itemEmpruntBookClass, itemReturnBookClass);
        menuEditStudent.getItems().addAll(itemAddStudent, itemRemoveStudent, itemModStudent, itemEmpruntBookStudent, itemReturnBookStudent);
        menuEdit.getItems().addAll(menuEditBooks, menuEditClass, menuEditStudent);
        menuBar.getMenus().addAll(menuFile, menuEdit);

        layout.add(menuBar, 0, 0);

        layout.add(this.centerPane, 0, 1);
        GridPane.setHgrow(this.centerPane, Priority.ALWAYS);
        GridPane.setVgrow(this.centerPane, Priority.ALWAYS);
    }

    public void showPanel(IPanel panel){
        this.centerPane.getChildren().clear();
        this.centerPane.getChildren().add(panel.getLayout());
        panel.init(this);
        manager.log("Affichage du panel : " + panel.getClass().getName());
        panel.onShow();
    }

    public Manager getManager() {
        return manager;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public void showRemoveBook(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Retirer un livre");

        GridPane panel = new GridPane();


        TextField isbnText = new TextField();
        isbnText.setPromptText("Entrez le code ISBN du livre");
        isbnText.setMinSize(300, 50);
        isbnText.setMaxSize(300, 50);
        isbnText.setFocusTraversable(false);
        GridPane.setValignment(isbnText, VPos.CENTER);
        GridPane.setHalignment(isbnText, HPos.CENTER);
        GridPane.setVgrow(isbnText, Priority.ALWAYS);
        GridPane.setHgrow(isbnText, Priority.ALWAYS);
        isbnText.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(50);
        button.setOnAction(e -> {
            this.getManager().removeBook(isbnText.getText());
            this.showPanel(new HomePanel());
            stage.close();
        });

        panel.getChildren().addAll(isbnText, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void modifyClass(byte nbClass){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Ajouter une classe");

        GridPane panel = new GridPane();
        Clazz clazz = null;

        for(int i = 0; i < getManager().getClasses().size(); i++){
            if(getManager().getClasses().get(i).getClassNumber() == nbClass){
                clazz = getManager().getClasses().get(i);
            }
        }


        final TextField numberText = new TextField();
        numberText.setPromptText("Entrez le numéro de la classe");
        numberText.setText(String.valueOf(clazz.getClassNumber()));
        numberText.setMinSize(350, 50);
        numberText.setMaxSize(350, 50);
        numberText.setFocusTraversable(false);
        GridPane.setValignment(numberText, VPos.CENTER);
        GridPane.setHalignment(numberText, HPos.CENTER);
        GridPane.setVgrow(numberText, Priority.ALWAYS);
        GridPane.setHgrow(numberText, Priority.ALWAYS);
        numberText.setTranslateY(-70);

        StringBuilder sb = new StringBuilder();
        ArrayList<String> names = new ArrayList<>(Arrays.asList(clazz.getTeacher().getNames()));

        for (String name : names) {
            sb.append(name).append(",");
        }

        TextField teacherNames = new TextField();
        teacherNames.setPromptText("Entrez les noms des maîtresses (séparés par des virgules)");
        teacherNames.setText(sb.substring(0, sb.toString().length()-1));
        teacherNames.setMinSize(350, 50);
        teacherNames.setMaxSize(350, 50);
        teacherNames.setFocusTraversable(false);
        GridPane.setValignment(teacherNames, VPos.CENTER);
        GridPane.setHalignment(teacherNames, HPos.CENTER);
        GridPane.setVgrow(teacherNames, Priority.ALWAYS);
        GridPane.setHgrow(teacherNames, Priority.ALWAYS);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(100);
        Clazz finalClazz = clazz;
        button.setOnAction(e -> {
            String[] tNames = teacherNames.getText().split(",");
            finalClazz.setTeacher(new Teacher(tNames));
            showPanel(new ClassPanel());
            stage.close();
        });

        panel.getChildren().addAll(numberText, teacherNames, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showModifyClass(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Modifier une classe");

        GridPane panel = new GridPane();


        TextField nbClass = new TextField();
        nbClass.setPromptText("Entrez le numéro de la classe");
        nbClass.setMinSize(300, 50);
        nbClass.setMaxSize(300, 50);
        nbClass.setFocusTraversable(false);
        GridPane.setValignment(nbClass, VPos.CENTER);
        GridPane.setHalignment(nbClass, HPos.CENTER);
        GridPane.setVgrow(nbClass, Priority.ALWAYS);
        GridPane.setHgrow(nbClass, Priority.ALWAYS);
        nbClass.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(50);
        button.setOnAction(e -> {
            modifyClass(Byte.parseByte(nbClass.getText()));
            stage.close();
        });

        panel.getChildren().addAll(nbClass, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showRemoveClass(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Retirer une Classe");

        GridPane panel = new GridPane();


        TextField numberText = new TextField();
        numberText.setPromptText("Entrez le numéro de la classe");
        numberText.setMinSize(300, 50);
        numberText.setMaxSize(300, 50);
        numberText.setFocusTraversable(false);
        GridPane.setValignment(numberText, VPos.CENTER);
        GridPane.setHalignment(numberText, HPos.CENTER);
        GridPane.setVgrow(numberText, Priority.ALWAYS);
        GridPane.setHgrow(numberText, Priority.ALWAYS);
        numberText.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(50);
        button.setOnAction(e -> {
            manager.removeClass(Byte.parseByte(numberText.getText()));
            showPanel(new ClassPanel());
            stage.close();
        });

        panel.getChildren().addAll(numberText, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showAddClass(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Ajouter une classe");

        GridPane panel = new GridPane();


        TextField numberText = new TextField();
        numberText.setPromptText("Entrez le numéro de la classe");
        numberText.setMinSize(350, 50);
        numberText.setMaxSize(350, 50);
        numberText.setFocusTraversable(false);
        GridPane.setValignment(numberText, VPos.CENTER);
        GridPane.setHalignment(numberText, HPos.CENTER);
        GridPane.setVgrow(numberText, Priority.ALWAYS);
        GridPane.setHgrow(numberText, Priority.ALWAYS);
        numberText.setTranslateY(-70);

        TextField teacherNames = new TextField();
        teacherNames.setPromptText("Entrez les noms des maîtresses (séparés par des virgules)");
        teacherNames.setMinSize(350, 50);
        teacherNames.setMaxSize(350, 50);
        teacherNames.setFocusTraversable(false);
        GridPane.setValignment(teacherNames, VPos.CENTER);
        GridPane.setHalignment(teacherNames, HPos.CENTER);
        GridPane.setVgrow(teacherNames, Priority.ALWAYS);
        GridPane.setHgrow(teacherNames, Priority.ALWAYS);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(100);
        button.setOnAction(e -> {
            String[] names = teacherNames.getText().split(",");
            getManager().addClasses(new Clazz(Byte.parseByte(numberText.getText()), new Teacher(names)));
            showPanel(new ClassPanel());
            stage.close();
        });

        panel.getChildren().addAll(numberText, teacherNames, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showAddStudent(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Ajouter un eleve");

        GridPane panel = new GridPane();


        TextField studentName = new TextField();
        studentName.setPromptText("Entrez le nom de l'eleve");
        studentName.setMinSize(350, 50);
        studentName.setMaxSize(350, 50);
        studentName.setFocusTraversable(false);
        GridPane.setValignment(studentName, VPos.CENTER);
        GridPane.setHalignment(studentName, HPos.CENTER);
        GridPane.setVgrow(studentName, Priority.ALWAYS);
        GridPane.setHgrow(studentName, Priority.ALWAYS);
        studentName.setTranslateY(-70);

        TextField classNumber = new TextField();
        classNumber.setPromptText("Entrez le numéro de la classe");
        classNumber.setMinSize(350, 50);
        classNumber.setMaxSize(350, 50);
        classNumber.setFocusTraversable(false);
        GridPane.setValignment(classNumber, VPos.CENTER);
        GridPane.setHalignment(classNumber, HPos.CENTER);
        GridPane.setVgrow(classNumber, Priority.ALWAYS);
        GridPane.setHgrow(classNumber, Priority.ALWAYS);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(100);
        button.setOnAction(e -> {
            getManager().getStudents().add(new Student(studentName.getText(), Byte.parseByte(classNumber.getText())));
            try {
                getManager().saveStudents();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showPanel(new StudentPanel());
            stage.close();
        });

        panel.getChildren().addAll(studentName, classNumber, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void modifyStudent(String studentName){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Modifier un eleve");

        GridPane panel = new GridPane();
        Student student = null;

        for(int i = 0; i < getManager().getStudents().size(); i++){
            if(getManager().getStudents().get(i).getName().equals(studentName)){
                student = getManager().getStudents().get(i);
            }
        }
        final TextField studentNameField = new TextField();
        studentNameField.setPromptText("Entrez le nom de l'eleve");
        studentNameField.setText(student.getName());
        studentNameField.setMinSize(350, 50);
        studentNameField.setMaxSize(350, 50);
        studentNameField.setFocusTraversable(false);
        GridPane.setValignment(studentNameField, VPos.CENTER);
        GridPane.setHalignment(studentNameField, HPos.CENTER);
        GridPane.setVgrow(studentNameField, Priority.ALWAYS);
        GridPane.setHgrow(studentNameField, Priority.ALWAYS);
        studentNameField.setTranslateY(-70);

        TextField classNumber = new TextField();
        classNumber.setPromptText("Le numéro de la classe)");
        classNumber.setText(String.valueOf(student.getClassNumber()));
        classNumber.setMinSize(350, 50);
        classNumber.setMaxSize(350, 50);
        classNumber.setFocusTraversable(false);
        GridPane.setValignment(classNumber, VPos.CENTER);
        GridPane.setHalignment(classNumber, HPos.CENTER);
        GridPane.setVgrow(classNumber, Priority.ALWAYS);
        GridPane.setHgrow(classNumber, Priority.ALWAYS);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(100);
        Student finalStudent = student;
        button.setOnAction(e -> {
            finalStudent.setName(studentNameField.getText());
            finalStudent.setClassNumber(Byte.parseByte(classNumber.getText()));
            try {
                getManager().saveStudents();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showPanel(new StudentPanel());
            stage.close();
        });

        panel.getChildren().addAll(studentNameField, classNumber, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showModifyStudent(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Modifier un eleve");

        GridPane panel = new GridPane();


        TextField studentName = new TextField();
        studentName.setPromptText("Entrez le nom de l'eleve");
        studentName.setMinSize(300, 50);
        studentName.setMaxSize(300, 50);
        studentName.setFocusTraversable(false);
        GridPane.setValignment(studentName, VPos.CENTER);
        GridPane.setHalignment(studentName, HPos.CENTER);
        GridPane.setVgrow(studentName, Priority.ALWAYS);
        GridPane.setHgrow(studentName, Priority.ALWAYS);
        studentName.setTranslateY(-50);

        TextField classNumber = new TextField();
        classNumber.setPromptText("Entrez le numéro de la classe");
        classNumber.setMinSize(300, 50);
        classNumber.setMaxSize(300, 50);
        classNumber.setFocusTraversable(false);
        GridPane.setValignment(classNumber, VPos.CENTER);
        GridPane.setHalignment(classNumber, HPos.CENTER);
        GridPane.setVgrow(classNumber, Priority.ALWAYS);
        GridPane.setHgrow(classNumber, Priority.ALWAYS);
        classNumber.setTranslateY(50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(150);
        button.setOnAction(e -> {
            modifyStudent(studentName.getText());
            stage.close();
        });

        panel.getChildren().addAll(classNumber, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showRemoveStudent(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Retirer un eleve");

        GridPane panel = new GridPane();


        TextField studentName = new TextField();
        studentName.setPromptText("Entrez le nom de l'eleve");
        studentName.setMinSize(300, 50);
        studentName.setMaxSize(300, 50);
        studentName.setFocusTraversable(false);
        GridPane.setValignment(studentName, VPos.CENTER);
        GridPane.setHalignment(studentName, HPos.CENTER);
        GridPane.setVgrow(studentName, Priority.ALWAYS);
        GridPane.setHgrow(studentName, Priority.ALWAYS);
        studentName.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(50);
        button.setOnAction(e -> {
            for(int i = 0; i < getManager().getStudents().size(); i++){
                if(getManager().getStudents().get(i).getName().equals(studentName.getText())){
                    manager.removeStudent(getManager().getStudents().get(i));
                }
            }
            try {
                getManager().saveStudents();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showPanel(new StudentPanel());
            stage.close();
        });

        panel.getChildren().addAll(studentName, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }
    public void showAddBook(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Ajouter un livre");

        GridPane panel = new GridPane();


        TextField isbnText = new TextField();
        isbnText.setPromptText("Entrez le code ISBN du livre");
        isbnText.setMinSize(300, 50);
        isbnText.setMaxSize(300, 50);
        isbnText.setFocusTraversable(false);
        GridPane.setValignment(isbnText, VPos.CENTER);
        GridPane.setHalignment(isbnText, HPos.CENTER);
        GridPane.setVgrow(isbnText, Priority.ALWAYS);
        GridPane.setHgrow(isbnText, Priority.ALWAYS);
        isbnText.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(50);
        button.setOnAction(e -> {
            try {
                if(this.getManager().createBook(isbnText.getText())){
                    this.showPanel(new HomePanel());
                    manager.log("Ajout d'un livre effectué");
                    stage.close();
                }else{
                    manager.warn("Ajout d'un livre impossible");
                    stage.close();
                }
            } catch (IOException ex) {
                manager.err("", ex);
            }
        });

        panel.getChildren().addAll(isbnText, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showModifyBook(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Modifier un livre");

        GridPane panel = new GridPane();


        TextField isbnText = new TextField();
        isbnText.setPromptText("Entrez le code ISBN du livre");
        isbnText.setMinSize(300, 50);
        isbnText.setMaxSize(300, 50);
        isbnText.setFocusTraversable(false);
        GridPane.setValignment(isbnText, VPos.CENTER);
        GridPane.setHalignment(isbnText, HPos.CENTER);
        GridPane.setVgrow(isbnText, Priority.ALWAYS);
        GridPane.setHgrow(isbnText, Priority.ALWAYS);
        isbnText.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(50);
        button.setOnAction(e -> getManager().getBooks().forEach(book -> {
            if(book.getIsbn().equals(isbnText.getText())){
                showModifyBookTwo(book);
                stage.close();
            }else{
                getManager().warn("Impossible de modifier le livre");
            }
        }));

        panel.getChildren().addAll(isbnText, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }
    public void showModifyBookTwo(Book book){
        Stage nStage = new Stage();
        nStage.setWidth(500);
        nStage.setHeight(800);
        nStage.setTitle("Modifier un livre");

        GridPane nPanel = new GridPane();


        TextField nIsbnText = new TextField();
        nIsbnText.setPromptText("Entrez le code ISBN du livre");
        nIsbnText.setText(book.getIsbn());
        nIsbnText.setMinSize(300, 50);
        nIsbnText.setMaxSize(300, 50);
        nIsbnText.setFocusTraversable(false);
        nIsbnText.setTranslateY(20);
        GridPane.setValignment(nIsbnText, VPos.TOP);
        GridPane.setHalignment(nIsbnText, HPos.CENTER);
        GridPane.setVgrow(nIsbnText, Priority.ALWAYS);
        GridPane.setHgrow(nIsbnText, Priority.ALWAYS);

        TextField name = new TextField();
        name.setPromptText("Entrez le nom du livre");
        name.setText(book.getName());
        name.setMinSize(300, 50);
        name.setMaxSize(300, 50);
        name.setFocusTraversable(false);
        name.setTranslateY(20+50+20);
        GridPane.setValignment(name, VPos.TOP);
        GridPane.setHalignment(name, HPos.CENTER);
        GridPane.setVgrow(name, Priority.ALWAYS);
        GridPane.setHgrow(name, Priority.ALWAYS);

        TextField published = new TextField();
        published.setPromptText("Entrez la date de publication");
        published.setText(book.getPublished());
        published.setMinSize(300, 50);
        published.setMaxSize(300, 50);
        published.setFocusTraversable(false);
        published.setTranslateY(20+50+20+50+20);
        GridPane.setValignment(published, VPos.TOP);
        GridPane.setHalignment(published, HPos.CENTER);
        GridPane.setVgrow(published, Priority.ALWAYS);
        GridPane.setHgrow(published, Priority.ALWAYS);

        TextField npublisher = new TextField();
        npublisher.setPromptText("Entrez le nom de l'editeur");
        npublisher.setText(book.getPublisher());
        npublisher.setMinSize(300, 50);
        npublisher.setMaxSize(300, 50);
        npublisher.setFocusTraversable(false);
        npublisher.setTranslateY(-130);
        GridPane.setValignment(npublisher, VPos.CENTER);
        GridPane.setHalignment(npublisher, HPos.CENTER);
        GridPane.setVgrow(npublisher, Priority.ALWAYS);
        GridPane.setHgrow(npublisher, Priority.ALWAYS);

        TextField auteur = new TextField();
        auteur.setPromptText("Entrez les noms des auteurs séparés de virgules");
        StringBuilder builder = new StringBuilder();
        for(String author : book.getAuthors()){
            builder.append(author).append(",");
        }
        auteur.setText(builder.toString());
        auteur.setMinSize(300, 50);
        auteur.setMaxSize(300, 50);
        auteur.setFocusTraversable(false);
        auteur.setTranslateY(-60);
        GridPane.setValignment(auteur, VPos.CENTER);
        GridPane.setHalignment(auteur, HPos.CENTER);
        GridPane.setVgrow(auteur, Priority.ALWAYS);
        GridPane.setHgrow(auteur, Priority.ALWAYS);

        TextField numberB = new TextField();
        numberB.setPromptText("Entrez le nombre de livre");
        numberB.setText(String.valueOf(book.getNumberOfBook()));
        numberB.setMinSize(300, 50);
        numberB.setMaxSize(300, 50);
        numberB.setFocusTraversable(false);
        numberB.setTranslateY(10);
        GridPane.setValignment(numberB, VPos.CENTER);
        GridPane.setHalignment(numberB, HPos.CENTER);
        GridPane.setVgrow(numberB, Priority.ALWAYS);
        GridPane.setHgrow(numberB, Priority.ALWAYS);

        TextField categoryB = new TextField();
        categoryB.setPromptText("Entrez la catégorie du livre");
        categoryB.setText(book.getCategory());
        categoryB.setMinSize(300, 50);
        categoryB.setMaxSize(300, 50);
        categoryB.setFocusTraversable(false);
        categoryB.setTranslateY(10+70);
        GridPane.setValignment(categoryB, VPos.CENTER);
        GridPane.setHalignment(categoryB, HPos.CENTER);
        GridPane.setVgrow(categoryB, Priority.ALWAYS);
        GridPane.setHgrow(categoryB, Priority.ALWAYS);

        TextField shelfB = new TextField();
        shelfB.setPromptText("Entrez l'étagère du livre");
        shelfB.setText(String.valueOf(book.getShelf()));
        shelfB.setMinSize(300, 50);
        shelfB.setMaxSize(300, 50);
        shelfB.setFocusTraversable(false);
        shelfB.setTranslateY(10 + 140);
        GridPane.setValignment(shelfB, VPos.CENTER);
        GridPane.setHalignment(shelfB, HPos.CENTER);
        GridPane.setVgrow(shelfB, Priority.ALWAYS);
        GridPane.setHgrow(shelfB, Priority.ALWAYS);

        TextField emprunted = new TextField();
        emprunted.setPromptText("Entrez le nombre de livre empruntés");
        emprunted.setText(String.valueOf(book.getEmpruntedBooks()));
        emprunted.setMinSize(300, 50);
        emprunted.setMaxSize(300, 50);
        emprunted.setFocusTraversable(false);
        emprunted.setTranslateY(10 + 210);
        GridPane.setValignment(emprunted, VPos.CENTER);
        GridPane.setHalignment(emprunted, HPos.CENTER);
        GridPane.setVgrow(emprunted, Priority.ALWAYS);
        GridPane.setHgrow(emprunted, Priority.ALWAYS);

        Button nButton = new Button("Valider");
        nButton.setMinSize(150, 30);
        nButton.setMaxSize(150, 30);
        nButton.setTranslateY(-10);
        GridPane.setValignment(nButton, VPos.BOTTOM);
        GridPane.setHalignment(nButton, HPos.CENTER);
        GridPane.setVgrow(nButton, Priority.ALWAYS);
        GridPane.setHgrow(nButton, Priority.ALWAYS);
        nButton.setOnAction(nEvent -> {
            String[] auteurs = auteur.getText().split(",");
            ArrayList<String> authors = new ArrayList<>();
            Collections.addAll(authors, auteurs);
            try {
                if(getManager().getBooks().contains(book)){
                    for (int i = 0; i < getManager().getBooks().size(); i++){
                        if(getManager().getBooks().get(i).getIsbn().equals(book.getIsbn())){
                            getManager().getBooks().remove(i);
                        }
                    }
                }
                Book nBook = new Book(nIsbnText.getText(), name.getText(), published.getText(), npublisher.getText(), authors, Integer.parseInt(numberB.getText()), Integer.parseInt(emprunted.getText()), Short.parseShort(shelfB.getText()), categoryB.getText());
                getManager().getBooks().add(nBook);
                getManager().saveBooks();
                showPanel(new HomePanel());
                nStage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });



        nPanel.getChildren().addAll(nIsbnText, name, published, npublisher, auteur, numberB, categoryB, shelfB, emprunted, nButton);

        nStage.setScene(new Scene(nPanel, 500,300));
        nStage.show();
    }

    public void showEmpruntBookStudent(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Emprunter un livre");

        GridPane panel = new GridPane();

        TextField studentName = new TextField();
        studentName.setPromptText("Entrez le nom de l'eleve");
        studentName.setMinSize(350, 50);
        studentName.setMaxSize(350, 50);
        studentName.setFocusTraversable(false);
        GridPane.setValignment(studentName, VPos.CENTER);
        GridPane.setHalignment(studentName, HPos.CENTER);
        GridPane.setVgrow(studentName, Priority.ALWAYS);
        GridPane.setHgrow(studentName, Priority.ALWAYS);
        studentName.setTranslateY(-120);

        TextField isbnCode = new TextField();
        isbnCode.setPromptText("Entrez le code ISBN du livre");
        isbnCode.setMinSize(350, 50);
        isbnCode.setMaxSize(350, 50);
        isbnCode.setFocusTraversable(false);
        GridPane.setValignment(isbnCode, VPos.CENTER);
        GridPane.setHalignment(isbnCode, HPos.CENTER);
        GridPane.setVgrow(isbnCode, Priority.ALWAYS);
        GridPane.setHgrow(isbnCode, Priority.ALWAYS);
        isbnCode.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(100);
        button.setOnAction(e -> {
            Student studentValid = null;
            Book bookValid = null;
            for(Student student : manager.getStudents()){
                if(student.getName().equals(studentName.getText())){
                    studentValid = student;
                    System.out.println(studentValid.getName());
                }
            }
            for (Book book : manager.getBooks()){
                if(book.getIsbn().equals(isbnCode.getText())){
                    bookValid = book;
                }
            }
            try {
                if(studentValid != null && bookValid != null){
                    getManager().empruntBook(studentValid, bookValid);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showPanel(new StudentPanel());
            stage.close();
        });

        panel.getChildren().addAll(studentName, isbnCode, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showReturnBookStudent(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Rendre un livre");

        GridPane panel = new GridPane();


        TextField studentName = new TextField();
        studentName.setPromptText("Entrez le nom de l'eleve");
        studentName.setMinSize(350, 50);
        studentName.setMaxSize(350, 50);
        studentName.setFocusTraversable(false);
        GridPane.setValignment(studentName, VPos.CENTER);
        GridPane.setHalignment(studentName, HPos.CENTER);
        GridPane.setVgrow(studentName, Priority.ALWAYS);
        GridPane.setHgrow(studentName, Priority.ALWAYS);
        studentName.setTranslateY(-120);

        TextField isbnCode = new TextField();
        isbnCode.setPromptText("Entrez le code ISBN du livre");
        isbnCode.setMinSize(350, 50);
        isbnCode.setMaxSize(350, 50);
        isbnCode.setFocusTraversable(false);
        GridPane.setValignment(isbnCode, VPos.CENTER);
        GridPane.setHalignment(isbnCode, HPos.CENTER);
        GridPane.setVgrow(isbnCode, Priority.ALWAYS);
        GridPane.setHgrow(isbnCode, Priority.ALWAYS);
        isbnCode.setTranslateY(-50);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(120);
        button.setOnAction(e -> {
            Student studentValid = null;
            Book bookValid = null;
            for(Student student : manager.getStudents()){
                if(student.getName().equals(studentName.getText())){
                    studentValid = student;
                }
            }
            for (Book book : manager.getBooks()){
                if(book.getIsbn().equals(isbnCode.getText())){
                    bookValid = book;
                }
            }
            try {
                if(studentValid != null && bookValid != null){
                    getManager().returnBook(studentValid, bookValid);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showPanel(new StudentPanel());
            stage.close();
        });

        panel.getChildren().addAll(studentName, isbnCode, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showEmpruntBookClass(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Emprunter un livre");

        GridPane panel = new GridPane();


        TextField numberText = new TextField();
        numberText.setPromptText("Entrez le numéro de la classe");
        numberText.setMinSize(350, 50);
        numberText.setMaxSize(350, 50);
        numberText.setFocusTraversable(false);
        GridPane.setValignment(numberText, VPos.CENTER);
        GridPane.setHalignment(numberText, HPos.CENTER);
        GridPane.setVgrow(numberText, Priority.ALWAYS);
        GridPane.setHgrow(numberText, Priority.ALWAYS);
        numberText.setTranslateY(-120);

        TextField isbnCode = new TextField();
        isbnCode.setPromptText("Entrez le code ISBN du livre");
        isbnCode.setMinSize(350, 50);
        isbnCode.setMaxSize(350, 50);
        isbnCode.setFocusTraversable(false);
        GridPane.setValignment(isbnCode, VPos.CENTER);
        GridPane.setHalignment(isbnCode, HPos.CENTER);
        GridPane.setVgrow(isbnCode, Priority.ALWAYS);
        GridPane.setHgrow(isbnCode, Priority.ALWAYS);
        isbnCode.setTranslateY(-50);

        TextField bookNumber = new TextField();
        bookNumber.setPromptText("Entrez le nombre de livre");
        bookNumber.setMinSize(350, 50);
        bookNumber.setMaxSize(350, 50);
        bookNumber.setFocusTraversable(false);
        GridPane.setValignment(bookNumber, VPos.CENTER);
        GridPane.setHalignment(bookNumber, HPos.CENTER);
        GridPane.setVgrow(bookNumber, Priority.ALWAYS);
        GridPane.setHgrow(bookNumber, Priority.ALWAYS);
        bookNumber.setTranslateY(20);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(120);
        button.setOnAction(e -> {
            Clazz clazzValid = null;
            Book bookValid = null;
            for(Clazz clazz : manager.getClasses()){
                if(clazz.getClassNumber() == Byte.parseByte(numberText.getText())){
                    clazzValid = clazz;
                }
            }
            for (Book book : manager.getBooks()){
                if(book.getIsbn().equals(isbnCode.getText())){
                    bookValid = book;
                }
            }
            try {
                if(clazzValid != null && bookValid != null){
                    getManager().empruntBook(clazzValid, bookValid, Integer.parseInt(bookNumber.getText()));
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showPanel(new ClassPanel());
            stage.close();
        });

        panel.getChildren().addAll(numberText, isbnCode, bookNumber, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }



    public void showReturnBookClass(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        stage.setTitle("Rendre un livre");

        GridPane panel = new GridPane();


        TextField numberText = new TextField();
        numberText.setPromptText("Entrez le numéro de la classe");
        numberText.setMinSize(350, 50);
        numberText.setMaxSize(350, 50);
        numberText.setFocusTraversable(false);
        GridPane.setValignment(numberText, VPos.CENTER);
        GridPane.setHalignment(numberText, HPos.CENTER);
        GridPane.setVgrow(numberText, Priority.ALWAYS);
        GridPane.setHgrow(numberText, Priority.ALWAYS);
        numberText.setTranslateY(-120);

        TextField isbnCode = new TextField();
        isbnCode.setPromptText("Entrez le code ISBN du livre");
        isbnCode.setMinSize(350, 50);
        isbnCode.setMaxSize(350, 50);
        isbnCode.setFocusTraversable(false);
        GridPane.setValignment(isbnCode, VPos.CENTER);
        GridPane.setHalignment(isbnCode, HPos.CENTER);
        GridPane.setVgrow(isbnCode, Priority.ALWAYS);
        GridPane.setHgrow(isbnCode, Priority.ALWAYS);
        isbnCode.setTranslateY(-50);

        TextField bookNumber = new TextField();
        bookNumber.setPromptText("Entrez le nombre de livre");
        bookNumber.setMinSize(350, 50);
        bookNumber.setMaxSize(350, 50);
        bookNumber.setFocusTraversable(false);
        GridPane.setValignment(bookNumber, VPos.CENTER);
        GridPane.setHalignment(bookNumber, HPos.CENTER);
        GridPane.setVgrow(bookNumber, Priority.ALWAYS);
        GridPane.setHgrow(bookNumber, Priority.ALWAYS);
        bookNumber.setTranslateY(20);

        Button button = new Button("Valider");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setVgrow(button, Priority.ALWAYS);
        GridPane.setHgrow(button, Priority.ALWAYS);
        button.setTranslateY(120);
        button.setOnAction(e -> {
            Clazz clazzValid = null;
            Book bookValid = null;
            for(Clazz clazz : manager.getClasses()){
                if(clazz.getClassNumber() == Byte.parseByte(numberText.getText())){
                    clazzValid = clazz;
                }
            }
            for (Book book : manager.getBooks()){
                if(book.getIsbn().equals(isbnCode.getText())){
                    bookValid = book;
                }
            }
            try {
                if(clazzValid != null && bookValid != null){
                    getManager().returnBook(clazzValid, bookValid, Integer.parseInt(bookNumber.getText()));
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            showPanel(new ClassPanel());
            stage.close();
        });

        panel.getChildren().addAll(numberText, isbnCode, bookNumber, button);

        stage.setScene(new Scene(panel, 500,300));
        stage.show();
    }

    public void showError(String error){

        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(200);
        stage.setTitle("Erreur");
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane panel = new GridPane();

        Label dateLabel = new Label(error);
        dateLabel.setFont(Font.font(dateLabel.getFont().getName(), 16));
        dateLabel.setTextFill(Color.WHITE);
        GridPane.setValignment(dateLabel, VPos.TOP);
        GridPane.setHalignment(dateLabel, HPos.CENTER);

        panel.getChildren().add(panel);
        stage.setScene(new Scene(panel, 500,100));
        stage.show();
    }

}
