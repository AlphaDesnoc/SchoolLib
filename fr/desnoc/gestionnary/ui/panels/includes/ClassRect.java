package fr.desnoc.gestionnary.ui.panels.includes;

import fr.desnoc.gestionnary.objects.Book;
import fr.desnoc.gestionnary.objects.Clazz;
import fr.desnoc.gestionnary.objects.Teacher;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
        top.setMaxHeight(60);
        top.setPrefHeight(60);

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

        List<Label> bookList = new ArrayList<>();
        int spaceX = 20;
        int spaceY = 80;
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

            spaceY += 35;
            t++;
            if(t == 4){
                t = 0;
                j++;
                spaceY = 80;
                spaceX += 120;
            }
            if(j == 2) break;
        }

        top.getChildren().addAll(classLabel, teacherName, bookLabel);
        top.getChildren().addAll(bookList);
        this.getChildren().addAll(top);
    }

    private void alwaysElement(Node element) {
        GridPane.setVgrow(element, Priority.ALWAYS);
        GridPane.setHgrow(element, Priority.ALWAYS);
    }
}
