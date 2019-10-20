
package com.gamesdatabase.gamerspileofshame.modelSuggestion;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutoSuggestion {

    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("executionTime")
    @Expose
    private Double executionTime;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("countResult")
    @Expose
    private Integer countResult;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Double executionTime) {
        this.executionTime = executionTime;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getCountResult() {
        return countResult;
    }

    public void setCountResult(Integer countResult) {
        this.countResult = countResult;
    }

}
