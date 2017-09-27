package com.example.palexis3.nearme.Responses;


import com.example.palexis3.nearme.Models.Result;
import com.google.gson.annotations.SerializedName;

public class ResultResponse {

    @SerializedName("result")
    Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
