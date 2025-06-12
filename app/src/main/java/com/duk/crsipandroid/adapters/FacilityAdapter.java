package com.duk.crsipandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.mvp.RubberFacility;

import java.util.List;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {

    private List<RubberFacility> items;
    private onItemClickListener listener;
    int[] bgColors = new int[]{
        R.color.app_yellow, R.color.app_green, R.color.red
    };

    public interface onItemClickListener{
        void onFacilityClick(RubberFacility item);
    }

    public void setItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public FacilityAdapter(List<RubberFacility> items){
        this.items = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facility, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RubberFacility item = items.get(position);
        holder.bind(item, position, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv_facility_item;
        TextView tv_facility_title, tv_facility_subtitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_facility_item = itemView.findViewById(R.id.cv_facility_item);
            tv_facility_title = itemView.findViewById(R.id.tv_facility_title);
            tv_facility_subtitle = itemView.findViewById(R.id.tv_facility_subtitle);
        }

        public void bind(RubberFacility item, int position, onItemClickListener listener){
            tv_facility_title.setText(item.title);
            tv_facility_subtitle.setText(item.subTitle);
            cv_facility_item.setCardBackgroundColor(
                    androidx.core.content.ContextCompat.getColor(itemView.getContext(), bgColors[(position%bgColors.length)])
            );
            cv_facility_item.setOnClickListener(v -> {
                if (listener != null){
                    listener.onFacilityClick(item);
                }
            });
        }
        //
    }
}
