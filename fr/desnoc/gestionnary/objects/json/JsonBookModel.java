package fr.desnoc.gestionnary.objects.json;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonBookModel {

    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("totalItems")
    @Expose
    public int totalItems;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;

    public int getTotalItems() {
        return totalItems;
    }
}