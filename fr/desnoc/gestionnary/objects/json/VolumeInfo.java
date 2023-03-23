package fr.desnoc.gestionnary.objects.json;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeInfo {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("subtitle")
    @Expose
    public String subtitle;
    @SerializedName("authors")
    @Expose
    public List<String> authors = null;
    @SerializedName("publisher")
    @Expose
    public String publisher;
    @SerializedName("publishedDate")
    @Expose
    public String publishedDate;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("industryIdentifiers")
    @Expose
    public List<IndustryIdentifier> industryIdentifiers = null;
    @SerializedName("readingModes")
    @Expose
    public ReadingModes readingModes;
    @SerializedName("pageCount")
    @Expose
    public int pageCount;
    @SerializedName("printType")
    @Expose
    public String printType;
    @SerializedName("categories")
    @Expose
    public List<String> categories = null;
    @SerializedName("maturityRating")
    @Expose
    public String maturityRating;
    @SerializedName("allowAnonLogging")
    @Expose
    public boolean allowAnonLogging;
    @SerializedName("contentVersion")
    @Expose
    public String contentVersion;
    @SerializedName("panelizationSummary")
    @Expose
    public PanelizationSummary panelizationSummary;
    @SerializedName("imageLinks")
    @Expose
    public ImageLinks imageLinks;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("previewLink")
    @Expose
    public String previewLink;
    @SerializedName("infoLink")
    @Expose
    public String infoLink;
    @SerializedName("canonicalVolumeLink")
    @Expose
    public String canonicalVolumeLink;

}