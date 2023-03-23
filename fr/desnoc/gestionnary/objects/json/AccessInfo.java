package fr.desnoc.gestionnary.objects.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessInfo {

    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("viewability")
    @Expose
    public String viewability;
    @SerializedName("embeddable")
    @Expose
    public boolean embeddable;
    @SerializedName("publicDomain")
    @Expose
    public boolean publicDomain;
    @SerializedName("textToSpeechPermission")
    @Expose
    public String textToSpeechPermission;
    @SerializedName("epub")
    @Expose
    public Epub epub;
    @SerializedName("pdf")
    @Expose
    public Pdf pdf;
    @SerializedName("webReaderLink")
    @Expose
    public String webReaderLink;
    @SerializedName("accessViewStatus")
    @Expose
    public String accessViewStatus;
    @SerializedName("quoteSharingAllowed")
    @Expose
    public boolean quoteSharingAllowed;

}