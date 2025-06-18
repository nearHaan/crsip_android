package com.duk.crsipandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duk.crsipandroid.R;
import com.duk.crsipandroid.mvp.WeatherForeCast;
import com.duk.crsipandroid.mvp.WeatherItem;
import com.google.android.material.timepicker.TimeFormat;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    List<WeatherItem> items;

    private final String ICON_BASE_URL = "https://openweathermap.org/img/wn/";
    private final String ICON_URL_SUFFIX = "@2x.png";

    public WeatherAdapter(List<WeatherItem> items){
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
        WeatherItem item = items.get(position);
        try {
            holder.bind(item);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

        private String formatToDeviceDate(String inputDate) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = inputFormat.parse(inputDate);
                DateFormat outputFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return inputDate;
            }
        }
        private String formatToWeekday(String inputDate) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = inputFormat.parse(inputDate);
                DateFormat outputFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return inputDate;
            }
        }

        private String formatToDeviceTime(String time) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                Date date = inputFormat.parse(time);
                DateFormat outputFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return time;
            }
        }

        void bind(WeatherItem item) throws ParseException {
            String iconURL = ICON_BASE_URL+item.getWeather().get(0).getIcon()+ICON_URL_SUFFIX;
            Glide.with(itemView.getContext())
                    .load(iconURL)
                    .placeholder(R.drawable.ic_weather_loading)
                    .error(R.drawable.ic_weather_error)
                    .into(iv_weatherRes);

            tv_date.setText(formatToDeviceDate(item.getDtTxt().split(" ")[0]));
            tv_day.setText(formatToWeekday(item.getDtTxt().split(" ")[0]));
            tv_time.setText(formatToDeviceTime(item.getDtTxt().split(" ")[1]));
            tv_weather.setText(item.getWeather().get(0).getMain().toUpperCase());
            tv_temp.setText(new DecimalFormat("#").format(item.getMain().getTemp())+"°C");
            tv_prec.setText(new DecimalFormat("#").format(item.getMain().getHumidity())+"%");
        }
    }
}
