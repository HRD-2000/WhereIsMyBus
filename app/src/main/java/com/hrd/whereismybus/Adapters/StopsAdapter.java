package com.hrd.whereismybus.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hrd.whereismybus.R;
import com.hrd.whereismybus.stops_pojo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StopsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Integer stop_no;
    Context context;
    List<stops_pojo> list;

    public static int stop1 = 1;
    public static int stop2 = 2;
    public static int stop3 = 3;

  /*  Integer total_stops;
    String stop_name;


    public StopsAdapter(Context context, Integer stop_no){
        this.stop_no = stop_no;
        this.context = context;
        this.total_stops = total_stops;
        this.stop_name = stop_name;
    }*/

    public StopsAdapter(Context context, List<stops_pojo> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == stop1){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop1_cardview, parent, false);
            return new View1(view);

        }else if (viewType == stop2){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop3_cardview, parent, false);
            return new View2(view);

        }else  { //

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop2_cardview, parent, false);
            return new View3(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position) == stop1) {
            ((View1) holder).stop_name.setText(list.get(position).getStop_name());
        }else if(getItemViewType(position) == stop2){
            ((View2) holder).stop_name.setText(list.get(position).getStop_name());
        }else if(getItemViewType(position) == stop3){
            ((View3) holder).stop_name.setText(list.get(position).getStop_name());
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getStop_no() == 1) {
            Log.d("Stops","stop1");
            return stop1;
        }else if(list.get(position).getStop_no() == list.size()){
            Log.d("Stops","stop3");
            return stop3;
        }else {
            Log.d("Stops","stop2");
            return stop2;
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

/*    public class myViewHolder extends RecyclerView.ViewHolder {

        ImageView im;
        TextView stop_name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            im = (ImageView) itemView.findViewById(R.id.imageView8);
            stop_name = (TextView) itemView.findViewById(R.id.stop_name);
        }
    }*/

    public class View1 extends RecyclerView.ViewHolder{

        @BindView(R.id.stop_name) TextView stop_name;

        public View1(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class View2 extends RecyclerView.ViewHolder{

        @BindView(R.id.stop_name) TextView stop_name;

        public View2(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class View3 extends RecyclerView.ViewHolder{

        @BindView(R.id.stop_name) TextView stop_name;

        public View3(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
