package fr.desnoc.gestionnary.ui.panels.includes;

import fr.desnoc.gestionnary.Main;
import fr.desnoc.gestionnary.objects.Book;
import fr.desnoc.gestionnary.objects.Clazz;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ClassRect extends GridPane {

    public ClassRect(Clazz clazz){
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(50,150);
        int green = randomGenerator.nextInt(50,150);
        int blue = randomGenerator.nextInt(50,150);

        alwaysElement(this);
        this.setWidth(400);
        this.setMaxWidth(400);
        this.setPrefWidth(400);
        this.setHeight(250);
        this.setMaxHeight(250);
        this.setPrefHeight(250);
        this.setStyle("-fx-background-color: rgb(" + red + "," + green + "," + blue +");");

        GridPane top = new GridPane();
        alwaysElement(top);
        GridPane.setValignment(top, VPos.TOP);
        this.setStyle("-fx-background-color: rgb(" + red + "," + green + "," + blue +");");
        top.setMaxWidth(400);
        top.setPrefWidth(400);
        top.setMaxHeight(50);
        top.setPrefHeight(50);

        GridPane bottom = new GridPane();
        alwaysElement(bottom);
        GridPane.setValignment(bottom, VPos.BOTTOM);
        this.setStyle("-fx-background-color: rgb(" + red + "," + green + "," + blue +");");
        bottom.setMaxWidth(400);
        bottom.setPrefWidth(400);
        bottom.setMaxHeight(140);
        bottom.setPrefHeight(140);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.BOTTOM);
        rowConstraints.setMinHeight(40);
        rowConstraints.setMaxHeight(40);
        rowConstraints.setVgrow(Priority.ALWAYS);
        this.getRowConstraints().addAll(rowConstraints, new RowConstraints());
        this.add(top, 0, 0);
        this.add(bottom, 0, 1);

        Label classLabel = new Label("Classe : " + clazz.getClassNumber());
        this.alwaysElement(classLabel);
        classLabel.setFont(Font.font(classLabel.getFont().getName(), FontWeight.BOLD, 16));
        classLabel.setTextFill(Color.WHITE);
        GridPane.setValignment(classLabel, VPos.TOP);
        GridPane.setHalignment(classLabel, HPos.CENTER);
        classLabel.setTranslateY(10);

        StringBuilder builder = new StringBuilder();
        List<String> names = Arrays.asList(clazz.getTeacher().getNames());
        switch (clazz.getTeacher().getNames().length){
            case 1:
                builder.append(names.get(0));
                break;
            case 2:
                builder.append(names.get(0)).append(" et ").append(names.get(1));
                break;
        }

        Label teacherName = new Label(builder.toString());
        this.alwaysElement(teacherName);
        teacherName.setFont(new Font(teacherName.getFont().toString(), 16));
        teacherName.setTextFill(Color.WHITE);
        GridPane.setValignment(teacherName, VPos.TOP);
        GridPane.setHalignment(teacherName, HPos.CENTER);
        teacherName.setTranslateY(30);

        Label bookLabel = new Label("Livres :");
        this.alwaysElement(bookLabel);
        bookLabel.setFont(Font.font(bookLabel.getFont().getName(), FontWeight.BOLD, 20));
        bookLabel.setTextFill(Color.WHITE);
        GridPane.setValignment(bookLabel, VPos.TOP);
        GridPane.setHalignment(bookLabel, HPos.CENTER);
        bookLabel.setTranslateY(70);

        showBookList(bottom, clazz);

        top.getChildren().addAll(classLabel, teacherName, bookLabel);
    }

    public void showBookList(GridPane panel, Clazz clazz){
        ScrollPane scrollPane = new ScrollPane();
        this.alwaysElement(scrollPane);
        scrollPane.getStylesheets().add(Main.class.getResource("/css/scrollbar.css").toExternalForm());

        int height = 50;

        GridPane centerPane = new GridPane();
        alwaysElement(centerPane);
        GridPane.setValignment(centerPane, VPos.BOTTOM);
        centerPane.setMaxWidth(400);
        centerPane.setMinWidth(400);

        List<Label> bookList = new ArrayList<>();
        int spaceX = 20;
        int spaceY = 10;
        int t = 0;
        int j = 0;
        int listSize = clazz.getEmpruntedBooks().size();
        for(int i = 0; i < listSize; i++) {
            Book book = clazz.getEmpruntedBooks().get(i);
            Label bookName = new Label("- " + book.getName());
            bookName.setTextFill(Color.WHITE);
            bookName.setFont(new Font(bookName.getFont().toString(), 15.0F));
            this.alwaysElement(bookName);
            GridPane.setValignment(bookName, VPos.BOTTOM);
            bookName.setTranslateX(spaceX);
            bookName.setTranslateY(spaceY);
            bookList.add(bookName);

            spaceY += 30;
            t++;
            if(t == 8){
                t = 0;
                j++;
                spaceY = 10;
                spaceX += 170;
            }
            if(listSize <= 8){
                height = 35 * listSize;
            }
            if(j == 2) break;
        }

        VBox vBox = new VBox();
        this.alwaysElement(vBox);
        vBox.setMinHeight(height);
        vBox.setMinWidth(380);
        vBox.setMaxWidth(380);
        vBox.setAlignment(Pos.TOP_CENTER);

        centerPane.getChildren().addAll(bookList);
        scrollPane.setContent(vBox);
        panel.getChildren().add(scrollPane);
        vBox.getChildren().add(0, centerPane);
    }

    private void alwaysElement(Node element) {
        GridPane.setVgrow(element, Priority.ALWAYS);
        GridPane.setHgrow(element, Priority.ALWAYS);
    }
}
