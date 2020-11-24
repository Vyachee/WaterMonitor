package com.gdev.watermonitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cache {
    Context context;
    Gson gson;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public Cache(Context context) {
        this.context = context;
        gson = new Gson();

        preferences = context.getSharedPreferences("water_info", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public List<WaterItem> getWaterInfo() {
        List<WaterItem> items = new ArrayList<>();
        String data = preferences.getString("water_items", null);
        if(data == null)
            return items;

        try {
            JSONArray array = new JSONArray(data);
            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                WaterItem item = new WaterItem(
                        Double.parseDouble(object.getString("amount")),
                        new Date(object.getString("date")),
                        Long.parseLong(object.getString("id"))
                );
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return items;
    }

    public void save(List<WaterItem> items) {
        String dataToSave = gson.toJson(items, items.getClass());
        editor.putString("water_items", dataToSave);
        editor.apply();
    }

    public WaterItem addWater(double amount) {
        List<WaterItem> waterItemList = getWaterInfo();

        if(waterItemList.size() == 0) {
            WaterItem item = new WaterItem(amount, new Date(), 1);
            waterItemList.add(item);
            save(waterItemList);
            return item;
        }

        WaterItem lastItem = waterItemList.get(waterItemList.size() - 1);

        WaterItem item = new WaterItem(amount, new Date(), lastItem.getId() + 1);
        waterItemList.add(item);

        save(waterItemList);

        return item;
    }

    public void removeWater(WaterItem item) {
        List<WaterItem> waterItemList = getWaterInfo();

        for(int i = 0; i < waterItemList.size(); i++) {
            WaterItem it = waterItemList.get(i);
            if(it.getId() == item.getId())
                waterItemList.remove(i);
        }

        save(waterItemList);
    }
}
