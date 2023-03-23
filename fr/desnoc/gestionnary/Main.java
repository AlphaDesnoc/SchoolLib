package fr.desnoc.gestionnary;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.desnoc.gestionnary.ui.FxApplication;

public class Main {

    private static Gson gson;
    public static void main(String[] args) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        FxApplication.launch(FxApplication.class);
    }

    public static Gson getGson() {
        return gson;
    }
}
