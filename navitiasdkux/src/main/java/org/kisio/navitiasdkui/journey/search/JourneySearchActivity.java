package org.kisio.navitiasdkui.journey.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.kisio.navitiasdkui.R;

public class JourneySearchActivity extends AppCompatActivity implements ResultAdapter.ClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onSolutionClick(ResultModel solution) {

    }
}
