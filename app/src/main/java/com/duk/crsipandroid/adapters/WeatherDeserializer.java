package com.duk.crsipandroid.adapters;

import com.duk.crsipandroid.mvp.WeatherItem;
import com.duk.crsipandroid.mvp.WeatherResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeatherDeserializer implements JsonDeserializer<WeatherResponse> {

    @Override
    public WeatherResponse deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray listArray = jsonObject.getAsJsonArray("list");

        List<WeatherItem> filteredList = new ArrayList<>();

        for (JsonElement element : listArray) {
            JsonObject item = element.getAsJsonObject();
            String dtTxt = item.get("dt_txt").getAsString();

            if (dtTxt.endsWith("12:00:00")) {
                WeatherItem weatherItem = context.deserialize(element, WeatherItem.class);
                filteredList.add(weatherItem);
                if (filteredList.size() >= 5) {
                    break;
                }
            }
        }

        WeatherResponse response = new WeatherResponse(filteredList);
        return response;
    }
}