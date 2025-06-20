package com.duk.crsipandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.mvp.PriceResponse;

import java.util.List;

public class PricePageAdapter extends RecyclerView.Adapter<PricePageAdapter.ViewHolder> {

    public List<PriceResponse> items;
    private PricePageViewAdapter pricePageViewAdapter;

    public PricePageAdapter(List<PriceResponse> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PriceResponse item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title;
        RecyclerView rv_rubber_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_price_page_title);
            rv_rubber_price = itemView.findViewById(R.id.rv_rubber_price);
        }

        void bind(PriceResponse item){
            tv_title.setText(item.MarketLocation);
            rv_rubber_price.setLayoutManager(new LinearLayoutManager(rv_rubber_price.getContext(), LinearLayoutManager.VERTICAL, false));
            pricePageViewAdapter = new PricePageViewAdapter(item.Categoryprice);
            rv_rubber_price.setAdapter(pricePageViewAdapter);
        }
    }
}
