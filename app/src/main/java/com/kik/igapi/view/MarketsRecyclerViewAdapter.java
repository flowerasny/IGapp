package com.kik.igapi.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kik.igapi.R;
import com.kik.igapi.model.data.Market;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketsRecyclerViewAdapter extends RecyclerView.Adapter<MarketsRecyclerViewAdapter.MarketViewHolder>{

    private Context context;
    private List<Market> markets;

    public MarketsRecyclerViewAdapter(Context context, List<Market> markets) {
        this.context = context;
        this.markets = markets;
    }

    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new MarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewHolder holder, int position) {
        Market market = markets.get(position);

        holder.tvName.setText(market.getInstrumentName());
        holder.tvOffer.setText(market.getDisplayOffer());
    }

    @Override
    public int getItemCount() {
        return markets.size();
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
        notifyDataSetChanged();
    }

    static class MarketViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_instrument_name)
        TextView tvName;
        @BindView(R.id.tv_market_offer)
        TextView tvOffer;


        MarketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
