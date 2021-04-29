package com.hrd.whereismybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hrd.whereismybus.Adapters.StopsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MapsRoute extends AppCompatActivity  {

    RecyclerView recyclerView;
    List<stops_pojo> list;
    Integer total_stops = 4;
    StopsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_route);

        recyclerView = findViewById(R.id.recyclerView_routes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        list.add(new stops_pojo("Darbar Chokdi",01));
        list.add(new stops_pojo("Eva Mall",02));
        list.add(new stops_pojo("VIER",03));
        list.add(new stops_pojo("Tulsidham",04));

        for (stops_pojo e:list) {
            String stop_name;
            Integer stop_no;
            stops_pojo p = new stops_pojo();
            stop_name = p.getStop_name();
            stop_no = p.getStop_no();
            adapter = new StopsAdapter(MapsRoute.this,stop_no,total_stops,stop_name);

        }

        recyclerView.setAdapter(adapter);

    }


}