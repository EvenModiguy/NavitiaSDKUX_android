package org.kisio.navitiasdkui.journey.roadmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.MapView;

import org.kisio.navitiasdkui.R;
import org.kisio.navitiasdkui.journey.ListModel;
import org.kisio.navitiasdkui.util.Configuration;

import static org.kisio.navitiasdkui.journey.search.JourneySearchActivity.JOURNEY;
import static org.kisio.navitiasdkui.journey.search.JourneySearchActivity.ORIGIN_KEY;

public class RoadmapActivity extends AppCompatActivity {
    private MapView mapView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadmap);

        Toolbar toolbar = findViewById(R.id.roadmap_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Configuration.getColor(this, R.attr.colorPrimary));
        toolbar.setElevation(0);
        toolbar.setTitle(R.string.roadmap);

        mapView = findViewById(R.id.roadmap_map_view);
        recyclerView = findViewById(R.id.roadmap_recycler_view);

        String originKey = getIntent().getStringExtra(ORIGIN_KEY);
        if (!getIntent().hasExtra(originKey)) {
            throw new UnsupportedOperationException("RoadmapActivity cannot be launched directly.");
        }

        final ListModel journey = getIntent().getParcelableExtra(JOURNEY);
        if (journey != null) {
            throw new UnsupportedOperationException("The journey solution cannot be null.");
        }
    }
}
