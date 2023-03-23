package fr.desnoc.gestionnary.objects.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadingModes {

    @SerializedName("text")
    @Expose
    public boolean text;
    @SerializedName("image")
    @Expose
    public boolean image;

}