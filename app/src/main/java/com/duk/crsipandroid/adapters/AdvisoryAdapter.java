package com.duk.crsipandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.mvp.AdvisoryItem;

import java.util.List;

public class AdvisoryAdapter extends RecyclerView.Adapter<AdvisoryAdapter.ViewHolder> {

    private List<AdvisoryItem> items;
    private onItemClickListener listener;

    public AdvisoryAdapter(List<AdvisoryItem> items){
        this.items = items;
    }
    @NonNull
    @Override
    public AdvisoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    public interface onItemClickListener {
        void onAdvisoryItemClick(AdvisoryItem item);
    }

    public void setItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdvisoryAdapter.ViewHolder holder, int position) {
        AdvisoryItem item = items.get(position);
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

        void bind(AdvisoryItem item, onItemClickListener listener) {
            iv_icon.setImageResource(item.icon);
            tv_title.setText(item.title);
            cv_item.setCardBackgroundColor(
                    androidx.core.content.ContextCompat.getColor(itemView.getContext(), item.bgColor)
            );
            cv_item.setOnClickListener(v -> {
                if (listener != null){
                    listener.onAdvisoryItemClick(item);
                }
            });
        }
    }
}
