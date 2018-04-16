package com.kik.igapi.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kik.igapi.R;

import com.kik.igapi.model.data.Market;
import com.kik.igapi.viewmodel.MarketsViewModel;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class MainActivity extends AppCompatActivity {

    private static final String LAST_COUNTRY_SELECTED = "LAST_COUNTRY_SELECTED";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_countries)
    Spinner spinnerCountries;
    @BindView(R.id.rv_markets)
    RecyclerView marketsRecyclerView;

    MarketsViewModel marketsViewModel;
    MarketsRecyclerViewAdapter marketsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        configureSpinner();
        configureRecyclerView();

        observeMarketsViewModel();
    }

    private void configureSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.countries,
                android.R.layout.simple_spinner_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountries.setAdapter(arrayAdapter);
    }


    private void configureRecyclerView() {
        marketsRecyclerViewAdapter = new MarketsRecyclerViewAdapter(this, new LinkedList<>());

        marketsRecyclerView.setHasFixedSize(true);
        marketsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        marketsRecyclerView.setAdapter(marketsRecyclerViewAdapter);
    }

    private void observeMarketsViewModel() {
        marketsViewModel = ViewModelProviders.of(this).get(MarketsViewModel.class);
        marketsViewModel.getMarkets().observe(this, this::displayMarkets);
    }

    private void displayMarkets(List<Market> markets){
        marketsRecyclerViewAdapter.setMarkets(markets);
    }

    @OnItemSelected(R.id.spinner_countries)
    public void countrySelected(AdapterView<?> adapterView){
        marketsViewModel.changeFilter(adapterView.getSelectedItem().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(LAST_COUNTRY_SELECTED, spinnerCountries.getSelectedItem().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(LAST_COUNTRY_SELECTED)){
            marketsViewModel.changeFilter(savedInstanceState.getString(LAST_COUNTRY_SELECTED));
        }super.onRestoreInstanceState(savedInstanceState);
    }
}
