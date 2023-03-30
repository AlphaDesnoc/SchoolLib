package fr.desnoc.gestionnary.ui.panels.includes;

import fr.desnoc.gestionnary.objects.Student;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

public class StudentRect extends GridPane {

    public StudentRect(Student student){
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(50,150);
        int green = randomGenerator.nextInt(50,150);
        int blue = randomGenerator.nextInt(50,150);

        alwaysElement(this);
        this.setWidth(300);
        this.setMaxWidth(300);
        this.setPrefWidth(300);
        this.setHeight(150);
        this.setMaxHeight(150);
        this.setPrefHeight(150);
        this.setStyle("-fx-background-color: rgb(" + red + "," + green + "," + blue +");");

        GridPane pane = new GridPane();
        alwaysElement(pane);
        GridPane.setValignment(pane, VPos.TOP);
        this.setStyle("-fx-background-color: rgb(" + red + "," + green + "," + blue +");");
        pane.setMaxWidth(300);
        pane.setPrefWidth(300);
        pane.setMaxHeight(150);
        pane.setPrefHeight(150);

        Label studentNameLabel = new Label(student.getName());
        this.alwaysElement(studentNameLabel);
        studentNameLabel.setFont(Font.font(studentNameLabel.getFont().getName(), FontWeight.BOLD, 16));
        studentNameLabel.setTextFill(Color.WHITE);
        GridPane.setValignment(studentNameLabel, VPos.TOP);
        GridPane.setHalignment(studentNameLabel, HPos.CENTER);
        studentNameLabel.setTranslateY(10);

        Label classLabel = new Label(student.getClassName());
        this.alwaysElement(classLabel);
        classLabel.setFont(Font.font(classLabel.getFont().getName(), FontWeight.LIGHT, 16));
        classLabel.setTextFill(Color.WHITE);
        GridPane.setValignment(classLabel, VPos.TOP);
        GridPane.setHalignment(classLabel, HPos.CENTER);
        classLabel.setTranslateY(30);

        Label bookLabel = new Label(student.getEmpruntedBooks().isEmpty() ? "Aucun livre" : "Livre : " + student.getEmpruntedBook().getName());
        this.alwaysElement(bookLabel);
        bookLabel.setFont(Font.font(bookLabel.getFont().getName(), 16));
        bookLabel.setTextFill(Color.WHITE);
        GridPane.setValignment(bookLabel, VPos.TOP);
        GridPane.setHalignment(bookLabel, HPos.CENTER);
        bookLabel.setTranslateY(70);

        Label dateLabel = new Label(student.getEmpruntedBooks().isEmpty() ? "" : "Depuis le : " + student.getEmpruntDate());
        this.alwaysElement(dateLabel);
        dateLabel.setFont(Font.font(dateLabel.getFont().getName(), 16));
        dateLabel.setTextFill(Color.WHITE);
        GridPane.setValignment(dateLabel, VPos.TOP);
        GridPane.setHalignment(dateLabel, HPos.CENTER);
        dateLabel.setTranslateY(100);

        pane.getChildren().addAll(studentNameLabel, classLabel, bookLabel, dateLabel);
        this.getChildren().add(pane);
    }

    private void alwaysElement(Node element) {
        GridPane.setVgrow(element, Priority.ALWAYS);
        GridPane.setHgrow(element, Priority.ALWAYS);
    }

}
