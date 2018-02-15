package org.kisio.sample.navitiasdkui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.joda.time.DateTime;
import org.kisio.navitiasdkui.util.Configuration;
import org.kisio.navitiasdkui.journey.search.JourneySearchActivity;
import org.kisio.navitiasdkui.journey.search.JourneysRequest;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.sdk_open);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), JourneySearchActivity.class);

                final JourneysRequest request = new JourneysRequest("2.3665844;48.8465337", "2.2979169;48.8848719");
                request.setOriginLabel("Home")
                    .setDestinationLabel("Work")
                    .setDatetime(DateTime.now().plusDays(1))
                    .setDatetimeRepresents("departure")
                    .setForbiddenUris(Arrays.asList("physical_mode:Bus"))
                    .setFirstSectionModes(Arrays.asList("bss"))
                    .setLastSectionModes(Arrays.asList("car"))
                    .setCount(5)
                ;

                intent.putExtra(JourneySearchActivity.INTENT_PARAM, request);
                startActivity(intent);
            }
        });
    }
}
