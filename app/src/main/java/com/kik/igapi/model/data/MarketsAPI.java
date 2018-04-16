package com.kik.igapi.model.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MarketsAPI {

    @SerializedName("markets")
    private List<Market> markets = null;

    public List<Market> getMarkets() {
        return markets;
    }
}