package fr.desnoc.gestionnary.objects.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageLinks {

    @SerializedName("smallThumbnail")
    @Expose
    public String smallThumbnail;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;

}