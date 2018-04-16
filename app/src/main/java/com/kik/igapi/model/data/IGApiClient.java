package com.kik.igapi.model.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGApiClient {

    @GET("{locale}/{country_code}")
    Call<MarketsAPI> marketsForCountry(
            @Path("locale") String locale,
            @Path("country_code") String countryCode
    );
}