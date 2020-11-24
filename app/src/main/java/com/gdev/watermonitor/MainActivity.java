/**
 *
*   Developed by Grinvald Vyacheslav 24/11/2020
 *   Napoleon IT School Android Development Online Course 2.0 / TEST TASK
*
* */

package com.gdev.watermonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_water_items;
    EditText et_amount;
    TextView tv_amount;
    ImageView iv_add;

    List<WaterItem> items;
    Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // dark icons in status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        cache = new Cache(this);
        initViews();
        initItems();

        reCalc();
    }

    public void reCalc() {
        List<WaterItem> items = cache.getWaterInfo();
        double amount = 0;
        for(WaterItem i : items)
            amount += i.getAmount();

        tv_amount.setText(amount + " мл");
    }

    public void initItems() {
        items = cache.getWaterInfo();


        WaterItemsAdapter adapter = new WaterItemsAdapter(this, items);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv_water_items.setAdapter(adapter);
        rv_water_items.setLayoutManager(manager);
    }

    public void initViews() {
        rv_water_items = findViewById(R.id.rv_water_items);
        et_amount = findViewById(R.id.et_amount);
        tv_amount = findViewById(R.id.tv_amount);
        iv_add = findViewById(R.id.iv_add);
    }

    public void add(View view) {
        String data = et_amount.getText().toString();
        double amount = Double.parseDouble(data);
        ((WaterItemsAdapter) rv_water_items.getAdapter()).addWater(amount);
        et_amount.setText("");
        reCalc();
    }
}