package com.duk.crsipandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.R;

import java.util.List;

public class BottomSheetLocationAdapter extends RecyclerView.Adapter<BottomSheetLocationAdapter.ViewHolder> {

    List<String> items;
    onItemClickListener listener;

    public interface onItemClickListener{
        void onSheetItemClick(String title, int position);
    }

    public BottomSheetLocationAdapter(List<String> items){
        this.items = items;
    }

    public void setItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bs_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = items.get(position);
        holder.bind(title, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ll_item;
        TextView tv_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_bs_location_item);
            ll_item = itemView.findViewById(R.id.ll_bs_location);
        }  void bind(String title, int position){
            tv_title.setText(title);
            ll_item.setOnClickListener(v -> {
                if (listener != null){
                    listener.onSheetItemClick(title, position);
                }
            });
        }
    }
}
