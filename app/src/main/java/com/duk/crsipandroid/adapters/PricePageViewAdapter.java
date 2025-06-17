package com.duk.crsipandroid.adapters;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.mvp.CategoryPrice;

import java.util.List;

public class PricePageViewAdapter extends RecyclerView.Adapter<PricePageViewAdapter.ViewHolder> {

    public List<CategoryPrice> items;

    public PricePageViewAdapter(List<CategoryPrice> items){
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
        CategoryPrice item = items.get(position);
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

        void bind(CategoryPrice item){
            tv_title.setText(item.CategoryName);
            tv_rup.setText(String.valueOf(item.rupeeamt));
            iv_rupStat.setImageResource(item.rupeeindicator.equals("increasing")?R.drawable.ic_arrow_upward_24:(item.rupeeindicator.equals("decreasing")?R.drawable.ic_arrow_downward_24:R.drawable.ic_equal_24));
            iv_rupStat.setImageTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.getContext(), item.rupeeindicator.equals("increasing")?R.color.app_green:(item.rupeeindicator.equals("decreasing")?R.color.red:R.color.app_yellow))));
            tv_dol.setText(String.valueOf(item.dollaramt));
            iv_dolStat.setImageResource(item.dollarindiator.equals("increasing")?R.drawable.ic_arrow_upward_24:(item.dollarindiator.equals("decreasing")?R.drawable.ic_arrow_downward_24:R.drawable.ic_equal_24));
            iv_dolStat.setImageTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.getContext(), item.dollarindiator.equals("increasing")?R.color.app_green:(item.dollarindiator.equals("decreasing")?R.color.red:R.color.app_yellow))));
        }
    }
}
