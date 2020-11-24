package com.gdev.watermonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WaterItemsAdapter extends RecyclerView.Adapter<WaterItemsAdapter.Holder> {

    Context context;
    List<WaterItem> waterItemList;
    Cache cache;

    public WaterItemsAdapter(Context context, List<WaterItem> waterItemList) {
        this.context = context;
        this.waterItemList = waterItemList;
        cache = new Cache(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.water_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        WaterItem item = waterItemList.get(position);
        holder.tv_amount.setText(String.format("%s мл", item.getAmount()));

        Date date = item.getDate();
        SimpleDateFormat format = new SimpleDateFormat("d.M.y k:m");
        holder.tv_date.setText(format.format(date));
    }

    @Override
    public int getItemCount() {
        return waterItemList.size();
    }

    public void addWater(double amount) {
        WaterItem item = cache.addWater(amount);
        waterItemList.add(item);
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView tv_amount;
        TextView tv_date;
        ImageView iv_delete;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            tv_date = itemView.findViewById(R.id.tv_date);

            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    WaterItem waterItem = waterItemList.get(index);

                    cache.removeWater(waterItem);
                    waterItemList.remove(index);

                    MainActivity activity = (MainActivity) context;
                    activity.reCalc();

                    notifyDataSetChanged();
                }
            });
        }
    }

}
