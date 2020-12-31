package com.collabapps.moca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.collabapps.moca.data.Event;
import com.google.android.material.textfield.TextInputLayout;

public class AddEventActivity extends AppCompatActivity {

    private TextInputLayout tilEventName, tilEventDate, tilEventLocation, tilEventDesc;

    public static Intent getIntentToNavigate(Context context) {
        final Intent addEventActivityIntent = new Intent(context, AddEventActivity.class);
        return addEventActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        setupUi();
    }

    private void setupUi() {
        initToolbar();
        initTextFields();
        initAddEventBtn();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.add_event_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.add_event_page_toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initTextFields() {
        tilEventName = findViewById(R.id.event_name_til);
        tilEventDate = findViewById(R.id.event_date_til);
        tilEventLocation = findViewById(R.id.event_location_til);
        tilEventDesc = findViewById(R.id.event_desc_til);
    }

    private void initAddEventBtn() {
        Button addEventButton = findViewById(R.id.add_event_btn);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddEvent();
            }
        });
    }

    private boolean isTextFieldEmpty() {
        return tilEventName.getEditText().getText().toString().isEmpty()
                || tilEventDate.getEditText().getText().toString().isEmpty()
                || tilEventLocation.getEditText().getText().toString().isEmpty()
                || tilEventDesc.getEditText().getText().toString().isEmpty();
    }

    private void displayFieldEmptySubmissionError() {
        Toast.makeText(this, "Please fill all fields before proceeding", Toast.LENGTH_LONG).show();
    }

    private Event getEventFromTextfields() {
        if (isTextFieldEmpty()) {
            displayFieldEmptySubmissionError();
        }

        String eventName = tilEventName.getEditText().getText().toString();
        String eventDate = tilEventDate.getEditText().getText().toString();
        String eventLocation = tilEventLocation.getEditText().getText().toString();
        String eventDesc = tilEventDesc.getEditText().getText().toString();

        return new Event(eventName, eventDate, eventLocation, eventDesc);
    }

    private void onClickAddEvent() {
        Event eventFromTextFields = getEventFromTextfields();
        //TODO: Send this 'Event' to MongoDB
    }
}