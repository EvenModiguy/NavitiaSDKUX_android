package org.kisio.navitiasdkui.journey.roadmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.MapView;

import org.kisio.navitiasdkui.R;

public class RoadmapActivity extends AppCompatActivity {
    private MapView mapView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadmap);

        mapView = findViewById(R.id.roadmap_map_view);
        recyclerView = findViewById(R.id.roadmap_recycler_view);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mapView.getLayoutParams().height = (int) 0.4 * metrics.heightPixels;
    }
}
