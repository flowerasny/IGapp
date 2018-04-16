package com.kik.igapi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.kik.igapi.R;
import com.kik.igapi.model.data.Market;
import com.kik.igapi.model.MarketsRepository;

import java.util.List;

public class MarketsViewModel extends AndroidViewModel {
    private MutableLiveData<String> filter = new MutableLiveData<>();
    private MarketsRepository repository = new MarketsRepository();
    private Resources resources = getApplication().getResources();

    public MarketsViewModel(@NonNull Application application) {
        super(application);
        changeFilter(getApplication().getResources().getString(R.string.default_country));
    }

    public void changeFilter(String country) {
        filter.setValue(country);
    }

    public LiveData<List<Market>> getMarkets() {
        return Transformations.switchMap(filter, this::getMarketsForCountry);
    }

    private LiveData<List<Market>> getMarketsForCountry(String country) {
        return repository.getMarketsFor(localeParamFor(country), countryCodeParamFor(country));
    }

    private String localeParamFor(String country) {
        switch (country){
            case "UK" : return resources.getString(R.string.uk_locale);
            case "Germany" : return resources.getString(R.string.germany_locale);
            case "France" : return resources.getString(R.string.france_locale);
            default: return resources.getString(R.string.default_locale);
        }
    }

    private String countryCodeParamFor(String country) {
        switch (country){
            case "UK" : return resources.getString(R.string.uk_country_code);
            case "Germany" : return resources.getString(R.string.germany_country_code);
            case "France" : return resources.getString(R.string.france_country_code);
            default: return resources.getString(R.string.default_country_code);
        }
    }

}
