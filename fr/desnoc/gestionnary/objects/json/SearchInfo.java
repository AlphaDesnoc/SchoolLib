package fr.desnoc.gestionnary.objects.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchInfo {

    @SerializedName("textSnippet")
    @Expose
    public String textSnippet;

}