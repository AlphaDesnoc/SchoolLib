package fr.desnoc.gestionnary.ui.panels.includes;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class ImageButton extends Button {

    private final ImageView imageView;

    public ImageButton(ImageView imageView) {
        this.imageView = imageView;
    }

    public Button build(){
        Button button = new Button();
        button.setGraphic(this.imageView);
        button.setStyle("-fx-background-color: transparent;");
        return button;
    }
}
