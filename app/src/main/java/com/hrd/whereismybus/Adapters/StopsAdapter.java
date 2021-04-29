package com.hrd.whereismybus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrd.whereismybus.R;

public class StopsAdapter extends RecyclerView.Adapter<StopsAdapter.myViewHolder> {

    Integer stop_no;
    Context context;
    Integer total_stops;
    String stop_name;

    public StopsAdapter(Context context, Integer stop_no,Integer total_stops,String stop_name){
        this.stop_no = stop_no;
        this.context = context;
        this.total_stops = total_stops;
        this.stop_name = stop_name;
    }


    @NonNull
    @Override
    public StopsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View final_view = null;

        View v1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop1_cardview, parent, false);

        View v2 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop2_cardview, parent, false);

        View v3 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stop3_cardview, parent, false);

        if (stop_no==1){
            final_view = v1;
            //Toast.makeText(context.getApplicationContext(), "final_view = v1", Toast.LENGTH_SHORT).show();
        }else if (total_stops == stop_no){
            final_view = v3;
            //Toast.makeText(context.getApplicationContext(), "final_view = v3", Toast.LENGTH_SHORT).show();
        }else {
            final_view = v2;
            //Toast.makeText(context.getApplicationContext(), "final_view = v1", Toast.LENGTH_SHORT).show();
        }

        return new myViewHolder(final_view);
    }

    @Override
    public void onBindViewHolder(@NonNull StopsAdapter.myViewHolder holder, int position) {


    }


    @Override
    public int getItemCount() {
        return total_stops;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView im;
        TextView stop_name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            im = (ImageView) itemView.findViewById(R.id.imageView8);
            stop_name = (TextView) itemView.findViewById(R.id.stop_name);
        }
    }
}
