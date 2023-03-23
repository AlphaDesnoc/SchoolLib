package fr.desnoc.gestionnary.ui.panels;

import fr.desnoc.gestionnary.managers.PanelManager;
import fr.desnoc.gestionnary.objects.Book;
import fr.desnoc.gestionnary.ui.panel.Panel;
import fr.desnoc.gestionnary.ui.panels.includes.LeftPanel;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HomePanel extends Panel {

    private final LeftPanel leftPanel = new LeftPanel();
    private final GridPane centerPanel = new GridPane();
    private final GridPane topPanel = new GridPane();
    private final GridPane bottomPanel = new GridPane();

    @Override
    public void init(PanelManager panelManager) {
        super.init(panelManager);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setMinWidth(150);
        columnConstraints.setMaxWidth(150);
        this.layout.getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());
        this.layout.add(leftPanel.getLayout(), 0, 0);
        this.leftPanel.init(this.panelManager);

        TableView<Book> tableBooks = new TableView<>();
        GridPane.setVgrow(tableBooks, Priority.ALWAYS);
        GridPane.setHgrow(tableBooks, Priority.ALWAYS);
        GridPane.setValignment(tableBooks, VPos.CENTER);
        GridPane.setHalignment(tableBooks, HPos.CENTER);
        tableBooks.setEditable(true);

        Comparator<String> columnComparator =
                Comparator.comparing(String::toLowerCase);

        TableColumn<Book, String> isbn = new TableColumn<>("ISBN");
        TableColumn<Book, String> bookName = new TableColumn<>("Nom");
        TableColumn<Book, List<String>> author = new TableColumn<>("Auteur");
        TableColumn<Book, String> publisher = new TableColumn<>("Editeur");
        TableColumn<Book, String> available = new TableColumn<>("Disponibilité");
        TableColumn<Book, Integer> nbBooks = new TableColumn<>("Nombre de livre");
        TableColumn<Book, Integer> empruntedBook = new TableColumn<>("Nombre Empruntés");
        TableColumn<Book, String> category = new TableColumn<>("Catégorie");
        TableColumn<Book, Short> shelf = new TableColumn<>("Etagère");

        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        author.setCellValueFactory(new PropertyValueFactory<>("authors"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        available.setCellValueFactory(new PropertyValueFactory<>("available"));
        nbBooks.setCellValueFactory(new PropertyValueFactory<>("numberOfBook"));
        empruntedBook.setCellValueFactory(new PropertyValueFactory<>("empruntedBooks"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        shelf.setCellValueFactory(new PropertyValueFactory<>("shelf"));

        ObservableList<Book> books = FXCollections.observableList(this.panelManager.getManager().getBooks());
        books.sort(Comparator.comparing(o -> o.getName().toLowerCase()));
        FilteredList<Book> flBooks = new FilteredList<>(books, p -> true);
        tableBooks.setItems(flBooks);
        tableBooks.sort();

        tableBooks.getColumns().addAll(isbn, bookName, author, publisher, available, nbBooks, empruntedBook, category, shelf);

        this.topPanel.getChildren().add(tableBooks);
        this.layout.add(centerPanel,1,0);
        GridPane.setHgrow(centerPanel, Priority.ALWAYS);
        GridPane.setVgrow(centerPanel, Priority.ALWAYS);

        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("ISBN", "Nom", "Catégorie", "Etagère");
        choiceBox.setValue("Nom");
        choiceBox.setMinSize(100, 50);
        choiceBox.setMaxSize(100, 50);
        choiceBox.setTranslateX(-150);
        GridPane.setValignment(choiceBox, VPos.CENTER);
        GridPane.setHalignment(choiceBox, HPos.CENTER);
        GridPane.setVgrow(choiceBox, Priority.ALWAYS);
        GridPane.setHgrow(choiceBox, Priority.ALWAYS);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Rechercher un livre");
        searchBar.setMinSize(400, 50);
        searchBar.setMaxSize(400, 50);
        searchBar.setTranslateX(100);
        GridPane.setValignment(searchBar, VPos.CENTER);
        GridPane.setHalignment(searchBar, HPos.CENTER);
        GridPane.setVgrow(searchBar, Priority.ALWAYS);
        GridPane.setHgrow(searchBar, Priority.ALWAYS);
        searchBar.textProperty().addListener(((obs, oldValue, newValue) -> {
            switch (choiceBox.getValue()){
                case "ISBN":
                    flBooks.setPredicate(p -> p.getIsbn().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Nom":
                    flBooks.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Catégorie":
                    flBooks.setPredicate(p -> p.getCategory().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Etagère":
                    flBooks.setPredicate(p -> String.valueOf(p.getShelf()).contains(newValue.trim()));
            }
        }));

        Button addBook = new Button("Ajouter un livre");
        addBook.setMinSize(120, 30);
        addBook.setMaxSize(120, 30);
        GridPane.setValignment(addBook, VPos.CENTER);
        GridPane.setHalignment(addBook, HPos.LEFT);
        GridPane.setVgrow(addBook, Priority.ALWAYS);
        GridPane.setHgrow(addBook, Priority.ALWAYS);
        addBook.setTranslateX(120);
        addBook.setTranslateY(-45);
        addBook.setOnAction(event -> {
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
                    if(panelManager.getManager().createBook(isbnText.getText())){
                        stage.close();
                        panelManager.showPanel(new HomePanel());
                    }else{
                        stage.close();
                        Stage nStage = new Stage();
                        nStage.setWidth(500);
                        nStage.setHeight(700);
                        nStage.setTitle("Ajouter un livre");

                        GridPane nPanel = new GridPane();


                        TextField nIsbnText = new TextField();
                        nIsbnText.setPromptText("Entrez le code ISBN du livre");
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
                        npublisher.setMinSize(300, 50);
                        npublisher.setMaxSize(300, 50);
                        npublisher.setFocusTraversable(false);
                        npublisher.setTranslateY(-80);
                        GridPane.setValignment(npublisher, VPos.CENTER);
                        GridPane.setHalignment(npublisher, HPos.CENTER);
                        GridPane.setVgrow(npublisher, Priority.ALWAYS);
                        GridPane.setHgrow(npublisher, Priority.ALWAYS);

                        TextField auteur = new TextField();
                        auteur.setPromptText("Entrez les noms des auteurs séparés de virgules");
                        auteur.setMinSize(300, 50);
                        auteur.setMaxSize(300, 50);
                        auteur.setFocusTraversable(false);
                        auteur.setTranslateY(-10);
                        GridPane.setValignment(auteur, VPos.CENTER);
                        GridPane.setHalignment(auteur, HPos.CENTER);
                        GridPane.setVgrow(auteur, Priority.ALWAYS);
                        GridPane.setHgrow(auteur, Priority.ALWAYS);

                        TextField numberB = new TextField();
                        numberB.setPromptText("Entrez le nombre de livre");
                        numberB.setMinSize(300, 50);
                        numberB.setMaxSize(300, 50);
                        numberB.setFocusTraversable(false);
                        numberB.setTranslateY((-10)+70);
                        GridPane.setValignment(numberB, VPos.CENTER);
                        GridPane.setHalignment(numberB, HPos.CENTER);
                        GridPane.setVgrow(numberB, Priority.ALWAYS);
                        GridPane.setHgrow(numberB, Priority.ALWAYS);

                        TextField categoryB = new TextField();
                        categoryB.setPromptText("Entrez la catégorie du livre");
                        categoryB.setMinSize(300, 50);
                        categoryB.setMaxSize(300, 50);
                        categoryB.setFocusTraversable(false);
                        categoryB.setTranslateY((-10)+140);
                        GridPane.setValignment(categoryB, VPos.CENTER);
                        GridPane.setHalignment(categoryB, HPos.CENTER);
                        GridPane.setVgrow(categoryB, Priority.ALWAYS);
                        GridPane.setHgrow(categoryB, Priority.ALWAYS);

                        TextField shelfB = new TextField();
                        shelfB.setPromptText("Entrez la catégorie du livre");
                        shelfB.setMinSize(300, 50);
                        shelfB.setMaxSize(300, 50);
                        shelfB.setFocusTraversable(false);
                        shelfB.setTranslateY((-10) + 210);
                        GridPane.setValignment(shelfB, VPos.CENTER);
                        GridPane.setHalignment(shelfB, HPos.CENTER);
                        GridPane.setVgrow(shelfB, Priority.ALWAYS);
                        GridPane.setHgrow(shelfB, Priority.ALWAYS);

                        Button nButton = new Button("Valider");
                        nButton.setMinSize(150, 30);
                        nButton.setMaxSize(150, 30);
                        nButton.setTranslateY(-40);
                        GridPane.setValignment(nButton, VPos.BOTTOM);
                        GridPane.setHalignment(nButton, HPos.CENTER);
                        GridPane.setVgrow(nButton, Priority.ALWAYS);
                        GridPane.setHgrow(nButton, Priority.ALWAYS);
                        nButton.setOnAction(nEvent -> {
                            /*try {

                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }*/
                        });

                        nPanel.getChildren().addAll(nIsbnText, name, published, npublisher, auteur, numberB, categoryB, shelfB, nButton);

                        nStage.setScene(new Scene(nPanel, 500,300));
                        nStage.show();
                    }
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            panel.getChildren().addAll(isbnText, button);

            stage.setScene(new Scene(panel, 500,300));
            stage.show();
        });

        Button removeBook = new Button("Retirer un livre");
        removeBook.setMinSize(120, 30);
        removeBook.setMaxSize(120, 30);
        GridPane.setValignment(removeBook, VPos.CENTER);
        GridPane.setHalignment(removeBook, HPos.LEFT);
        GridPane.setVgrow(removeBook, Priority.ALWAYS);
        GridPane.setHgrow(removeBook, Priority.ALWAYS);
        removeBook.setTranslateX(120);
        removeBook.setTranslateY(45);
        removeBook.setOnAction(e -> panelManager.showRemoveBook());

        Button modifyBook = new Button("Modifier un livre");
        modifyBook.setMinSize(120, 30);
        modifyBook.setMaxSize(120, 30);
        GridPane.setValignment(modifyBook, VPos.CENTER);
        GridPane.setHalignment(modifyBook, HPos.LEFT);
        GridPane.setVgrow(modifyBook, Priority.ALWAYS);
        GridPane.setHgrow(modifyBook, Priority.ALWAYS);
        modifyBook.setTranslateX(120);
        modifyBook.setOnAction(e -> panelManager.showModifyBook());

        this.bottomPanel.getChildren().addAll(addBook, removeBook, modifyBook, choiceBox, searchBar);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.BOTTOM);
        rowConstraints.setMinHeight(500);
        rowConstraints.setMaxHeight(500);
        rowConstraints.setVgrow(Priority.ALWAYS);
        this.centerPanel.getRowConstraints().addAll(rowConstraints, new RowConstraints());
        this.centerPanel.add(topPanel, 0, 0);
        this.centerPanel.add(bottomPanel, 0, 1);

        GridPane.setVgrow(topPanel, Priority.ALWAYS);
        GridPane.setHgrow(topPanel, Priority.ALWAYS);
        GridPane.setVgrow(bottomPanel, Priority.ALWAYS);
        GridPane.setHgrow(bottomPanel, Priority.ALWAYS);

    }

}
