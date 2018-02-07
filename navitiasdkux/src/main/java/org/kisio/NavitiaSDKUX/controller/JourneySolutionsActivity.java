package org.kisio.NavitiaSDKUX.controller;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;

import org.kisio.NavitiaSDKUX.config.Configuration;
import org.kisio.NavitiaSDKUX.R;
import org.kisio.NavitiaSDKUX.screen.JourneySolutionsScreen;

public class JourneySolutionsActivity extends AppCompatActivity {
    public enum IntentParameters {
        parameters
    }

    private static final String TAG = JourneySolutionsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            actionBar.setTitle(R.string.journeys);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        Configuration.colors.setTertiary(fetchPrimaryColor());

        final ComponentContext c = new ComponentContext(this);
        final JourneySolutionsScreen.Builder builder = JourneySolutionsScreen.create(c);

        setProps(builder);

        try {
            final Component<JourneySolutionsScreen> screenComponent = builder.build();
            final LithoView lithoView = LithoView.create(
                this,
                screenComponent
            );
            lithoView.setBackgroundColor(Configuration.colors.getLighterGray());
            setContentView(lithoView);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setProps(JourneySolutionsScreen.Builder builder) {
        final Intent intent = getIntent();

        final JourneySolutionsInParameters parameters = intent.getParcelableExtra(IntentParameters.parameters.name());
        if (parameters != null) {
            builder.initParameters(parameters);
        }
    }

    private int fetchPrimaryColor() {
        TypedValue typedValue = new TypedValue();

        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }
}
