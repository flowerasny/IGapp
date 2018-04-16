package com.kik.igapi.model.data;

import com.google.gson.annotations.SerializedName;

public class Market {
    @SerializedName("instrumentName")
    private String instrumentName;
    @SerializedName("displayOffer")
    private String displayOffer;

    public String getInstrumentName() {
        return instrumentName;
    }

    public String getDisplayOffer() {
        return displayOffer;
    }

}
