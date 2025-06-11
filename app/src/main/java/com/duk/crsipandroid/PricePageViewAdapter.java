package com.duk.crsipandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PricePageViewAdapter extends RecyclerView.Adapter<PricePageViewAdapter.ViewHolder> {

    public List<PricePageRowItem> items;

    public PricePageViewAdapter(List<PricePageRowItem> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PricePageRowItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title, tv_rup, tv_dol;
        ImageView iv_rupStat, iv_dolStat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_price_row_title);
            tv_rup = itemView.findViewById(R.id.tv_price_row_rup);
            tv_dol = itemView.findViewById(R.id.tv_price_row_dol);
            iv_rupStat = itemView.findViewById(R.id.iv_price_row_rupRes);
            iv_dolStat = itemView.findViewById(R.id.iv_price_row_dolRes);
        }

        void bind(PricePageRowItem item){
            tv_title.setText(item.title);
            tv_rup.setText(item.rup);
            iv_rupStat.setImageResource(item.rupStat);
            tv_dol.setText(item.dol);
            iv_dolStat.setImageResource(item.dolStat);
        }
    }
}
