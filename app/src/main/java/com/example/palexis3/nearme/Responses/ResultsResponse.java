package com.example.palexis3.nearme.Responses;

import com.example.palexis3.nearme.Models.Result;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultsResponse {

    @SerializedName("results")
    ArrayList<Result> results;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}
