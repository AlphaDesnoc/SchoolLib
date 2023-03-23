
package fr.desnoc.gestionnary.objects.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Epub {

    @SerializedName("isAvailable")
    @Expose
    public boolean isAvailable;
    @SerializedName("acsTokenLink")
    @Expose
    public String acsTokenLink;

}