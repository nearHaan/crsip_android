package com.duk.crsipandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.mvp.WeatherForeCast;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    List<WeatherForeCast> items;

    public WeatherAdapter(List<WeatherForeCast> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherForeCast item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_date, tv_day, tv_time, tv_weather, tv_temp, tv_prec;
        ImageView iv_weatherRes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date_w);
            tv_day = itemView.findViewById(R.id.tv_day_w);
            tv_time = itemView.findViewById(R.id.tv_time_w);
            iv_weatherRes = itemView.findViewById(R.id.iv_weatherRes_w);
            tv_weather = itemView.findViewById(R.id.tv_weather_w);
            tv_temp = itemView.findViewById(R.id.tv_temp_w);
            tv_prec = itemView.findViewById(R.id.tv_prec_w);

        }

        void bind(WeatherForeCast item){
            tv_date.setText(item.date);
            tv_day.setText(item.day);
            tv_time.setText(item.time);
            iv_weatherRes.setImageResource(item.weatherRes);
            tv_weather.setText(item.weather);
            tv_temp.setText(item.temp);
            tv_prec.setText(item.precp);
        }
    }
}
