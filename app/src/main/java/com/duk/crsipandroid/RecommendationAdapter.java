package com.duk.crsipandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<RecommendationItem> items;

    public RecommendationAdapter(List<RecommendationItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendationItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv_item;
        ImageView iv_icon;
        TextView tv_title;

        ViewHolder(View itemView) {
            super(itemView);
            cv_item = itemView.findViewById(R.id.cv_item);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_title = itemView.findViewById(R.id.tv_title);
        }

        void bind(RecommendationItem item) {
            cv_item.setCardBackgroundColor(
                    androidx.core.content.ContextCompat.getColor(itemView.getContext(), item.bgColor)
            );
            iv_icon.setImageResource(item.icon);
            tv_title.setText(item.title);
        }
    }
}