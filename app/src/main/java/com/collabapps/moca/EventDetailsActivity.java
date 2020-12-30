package com.collabapps.moca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.collabapps.moca.data.Event;

public class EventDetailsActivity extends AppCompatActivity {

    private Event event;
    private ;

    public static final String EVENT_DETAILS_EXTRAS_KEY ="event_details_extras_key";

    public static Intent getIntentToNavigate(Context context, Event event) {
        final Intent addEventActivityIntent = new Intent(context, AddEventActivity.class);
        addEventActivityIntent.putExtra(EVENT_DETAILS_EXTRAS_KEY, event);
        return addEventActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        setupUi();
    }

    private void setupUi() {
        initToolbar();
        setupLiveEventIndicator();
        initJoinEventBtn();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.event_details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupLiveEventIndicator() {
        event = getEventFromIntent();
        TextView liveEventIndicator = findViewById(R.id.live_event_indicator);

        if (event.isEventLive()) {
            liveEventIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void initJoinEventBtn() {

    }

    private Event getEventFromIntent() {
        if (!getIntent().hasExtra(EVENT_DETAILS_EXTRAS_KEY)) {
            throw new IllegalStateException("Please use EventDetailsActivity.getIntentToNavigate()");
            return null;
        }
        return (Event) getIntent().getExtras().get(EVENT_DETAILS_EXTRAS_KEY);
    }
}