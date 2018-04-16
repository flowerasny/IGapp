package com.kik.igapi.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kik.igapi.model.data.MarketsAPI;
import com.kik.igapi.model.data.IGApiClient;
import com.kik.igapi.model.data.Market;

import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarketsRepository {
    private static final String TAG = "MarketsRepository";
    private static final String API_BASE_URL = "https://api.ig.com/deal/samples/markets/ANDROID_PHONE/";

    private MutableLiveData<List<Market>> markets = new MutableLiveData<>();
    private IGApiClient client;

    public MarketsRepository(){
        configureRetrofit();
    }

    private void configureRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();

        client = retrofit.create(IGApiClient.class);
    }

    public LiveData<List<Market>> getMarketsFor(String locale, String countryCode){
        loadMarkets(locale, countryCode);
        return markets;
    }

    private void loadMarkets(String locale, String countryCode) {
        Call<MarketsAPI> call = client.marketsForCountry(locale, countryCode);
        call.enqueue(new Callback<MarketsAPI>() {
            @Override
            public void onResponse(@NonNull Call<MarketsAPI> call, @NonNull Response<MarketsAPI> response) {
                if (response.isSuccessful()) {
                    sortAndApplyResults(response.body().getMarkets());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MarketsAPI> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: loading data failed");
            }
        });
    }

    private void sortAndApplyResults(List<Market> markets) {
        sortAlphabetically(markets);
        setListToLiveData(markets);
    }

    private void sortAlphabetically(List<Market> markets) {
        Collections.sort(markets, (market1, market2) -> market1.getInstrumentName().compareTo(market2.getInstrumentName()));
    }

    private void setListToLiveData(List<Market> markets) {
        this.markets.setValue(markets);
    }
}
