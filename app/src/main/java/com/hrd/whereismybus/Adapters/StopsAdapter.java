package com.hrd.whereismybus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrd.whereismybus.R;

public class StopsAdapter extends RecyclerView.Adapter<routesAdapter.myViewHolder> {

    Integer stop_no;
    Context context;

    public StopsAdapter(Context context, Integer stop_no){
        this.stop_no = stop_no;
        this.context = context;
    }


    @NonNull
    @Override
    public routesAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View final_view = null;

        View v1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop1_cardview, parent, false);

        View v2 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop2_cardview, parent, false);

        View v3 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop3_cardview, parent, false);

        if (stop_no==1){
            final_view = v1;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull routesAdapter.myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
