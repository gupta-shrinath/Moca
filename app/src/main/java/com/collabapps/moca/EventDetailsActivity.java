package com.collabapps.moca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.collabapps.moca.data.Event;

public class EventDetailsActivity extends AppCompatActivity {

    private Event event;

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
        populateEventData();
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

    private void populateEventData() {
        TextView eventName = findViewById(R.id.details_event_name);
        TextView eventOrganiserName = findViewById(R.id.details_event_organiser);
        TextView eventDate = findViewById(R.id.details_event_date);
        TextView eventLocation = findViewById(R.id.details_event_location);
        TextView eventDesc = findViewById(R.id.details_event_desc);

        eventName.setText(event.getEventName());
        eventOrganiserName.setText(event.getEventOrganiserName());
        eventDate.setText(event.getEventDate());
        eventLocation.setText(event.getEventLocation());
        eventDesc.setText(event.getEventDesc());
    }

    private void initJoinEventBtn() {
        Button joinEventBtn = findViewById(R.id.join_event_btn);
        joinEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickJoinEventBtn();
            }
        });
    }

    private void onClickJoinEventBtn() {
        //TODO: Use a URI to take the user to the respective video calling app
    }

    private Event getEventFromIntent() {
        if (!getIntent().hasExtra(EVENT_DETAILS_EXTRAS_KEY)) {
            throw new IllegalStateException("Please use EventDetailsActivity.getIntentToNavigate()");
        }
        return (Event) getIntent().getExtras().get(EVENT_DETAILS_EXTRAS_KEY);
    }
}