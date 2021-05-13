package com.hrd.whereismybus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.hrd.whereismybus.MapsActivity;
import com.hrd.whereismybus.MapsRoute;
import com.hrd.whereismybus.R;
import com.hrd.whereismybus.Pojo.route_pojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class routesAdapter extends RecyclerView.Adapter<routesAdapter.myViewHolder> {

    Context context;
    List<route_pojo> list;


    public routesAdapter(Context context,List<route_pojo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_route_cardview, parent, false);

        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        route_pojo p = list.get(position);
        holder.driver_name.setText(p.getName());
        holder.phone_no.setText(p.getPhone_no());
        holder.start_location.setText(p.getS_location());
        holder.end_location.setText(p.getE_location());

        final String origin = p.getS_location();
        final String destination = p.getE_location();


        Picasso.get().load(p.getProfile()).into(holder.sim);
        holder.start_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MapsActivity.class));
            }
        });
        holder.phone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+p.getPhone_no()));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView sim;
        TextView driver_name,phone_no,start_location,end_location;
        ImageButton start_route,phone_icon;
        ShapeableImageView start_location_icon,end_location_icon;
        View v;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            sim = (ShapeableImageView) itemView.findViewById(R.id.shapeableImageView);
            driver_name = (TextView) itemView.findViewById(R.id.textView2);
            phone_no = (TextView) itemView.findViewById(R.id.textView3);
            start_location = (TextView) itemView.findViewById(R.id.textView4);
            end_location = (TextView) itemView.findViewById(R.id.textView5);
            start_route = (ImageButton) itemView.findViewById(R.id.start_route_btn);
            start_location_icon = (ShapeableImageView) itemView.findViewById(R.id.imageView3);
            end_location_icon = (ShapeableImageView) itemView.findViewById(R.id.imageView4);
            phone_icon = (ImageButton) itemView.findViewById(R.id.imageView2);
            v = (View) itemView.findViewById(R.id.view);

        }
    }
}
