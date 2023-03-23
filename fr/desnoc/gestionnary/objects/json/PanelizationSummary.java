package fr.desnoc.gestionnary.objects.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PanelizationSummary {

    @SerializedName("containsEpubBubbles")
    @Expose
    public boolean containsEpubBubbles;
    @SerializedName("containsImageBubbles")
    @Expose
    public boolean containsImageBubbles;

}