package com.duk.crsipandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.mvp.RecommendationItem;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private List<RecommendationItem> items;
    private OnItemClickListener listener;

    public RecommendationAdapter(List<RecommendationItem> items) {
        this.items = items;
    }

    public interface OnItemClickListener{
        void onRecommendationClick(RecommendationItem item);
    }

    public void setItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendationItem item = items.get(position);
        holder.bind(item, listener);
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

        void bind(RecommendationItem item, OnItemClickListener listener) {
            cv_item.setCardBackgroundColor(
                    androidx.core.content.ContextCompat.getColor(itemView.getContext(), item.bgColor)
            );
            cv_item.setOnClickListener(v -> {
                if (listener != null){
                    listener.onRecommendationClick(item);
                }
            });
            iv_icon.setImageResource(item.icon);
            tv_title.setText(item.title);
        }
    }
}