package org.kisio.sample.NavitiaSDKUX;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.joda.time.DateTime;
import org.kisio.NavitiaSDKUX.Config.Configuration;
import org.kisio.NavitiaSDKUX.Controllers.JourneySolutionsActivity;
import org.kisio.NavitiaSDKUX.Controllers.JourneySolutionsInParameters;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Insert your Navitia token
        Configuration.token = "";

        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.sdk_open);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), JourneySolutionsActivity.class);

                final JourneySolutionsInParameters parameters = new JourneySolutionsInParameters("2.3665844;48.8465337", "2.2979169;48.8848719");
                parameters.originLabel = "Chez moi";
                parameters.destinationLabel = "Au travail";
                parameters.datetime = DateTime.now().plusDays(1);
                parameters.datetimeRepresents = "departure";
                parameters.forbiddenUris = Arrays.asList("physical_mode:Bus");
                parameters.firstSectionModes = Arrays.asList("bss");
                parameters.lastSectionModes = Arrays.asList("car");
                parameters.count = 5;

                intent.putExtra(JourneySolutionsActivity.IntentParameters.parameters.name(), parameters);

                startActivity(intent);
            }
        });
    }
}
