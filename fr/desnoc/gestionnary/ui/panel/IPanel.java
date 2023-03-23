package fr.desnoc.gestionnary.ui.panel;

import fr.desnoc.gestionnary.managers.PanelManager;
import javafx.scene.layout.GridPane;

public interface IPanel {

    void init(PanelManager panelManager);
    GridPane getLayout();
    void onShow();

}
