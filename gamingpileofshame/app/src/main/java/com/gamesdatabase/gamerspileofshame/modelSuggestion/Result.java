
package com.gamesdatabase.gamerspileofshame.modelSuggestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("platform")
    @Expose
    private String platform;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
