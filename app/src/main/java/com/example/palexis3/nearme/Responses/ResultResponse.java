package com.example.palexis3.nearme.Responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.palexis3.nearme.Models.Result;

public class ResultResponse {

    @SerializedName("result")
    @Expose
    Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
