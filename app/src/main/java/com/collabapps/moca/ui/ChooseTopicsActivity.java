package com.collabapps.moca.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.collabapps.moca.Constants;
import com.collabapps.moca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

// TODO: Go to this page only the first time (use SharedPreferences)
// TODO: Get all the user selected topics on a different thread
// TODO: Store all selected topics in Firebase Database

public class ChooseTopicsActivity extends AppCompatActivity {

    private String googleUserAccountId;
    private DatabaseReference mDb = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_NAME);

    public static final String CHOOSE_TOPICS_ACTIVITY_KEY = "choose_topics_activity_key";

    public static Intent getIntentToNavigate(Context context, String googleUserAccountId) {
        Intent userAuthActivityIntent = new Intent(context, ChooseTopicsActivity.class);
        userAuthActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        userAuthActivityIntent.putExtra(CHOOSE_TOPICS_ACTIVITY_KEY, googleUserAccountId);
        return userAuthActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topics);

        Button submitTopicsBtn = findViewById(R.id.submit_topics_btn);
        submitTopicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistUserTopicChoiceToOnlineDatabase();
            }
        });

        if (getIntent().hasExtra(CHOOSE_TOPICS_ACTIVITY_KEY)) {
            googleUserAccountId = getIntent().getExtras().getString(CHOOSE_TOPICS_ACTIVITY_KEY);
        }
    }

    private List<String> getUserSelectedTopics() {
        ChipGroup topicsChipGroup = findViewById(R.id.topics_chipGroup);
        List<String> userSelectedTopicsList = new ArrayList<>();

        for (int selectedChipId : topicsChipGroup.getCheckedChipIds()) {
            Chip selectedChip = findViewById(selectedChipId);
            userSelectedTopicsList.add(selectedChip.getText().toString());
        }

        return userSelectedTopicsList;
    }

    private void persistUserTopicChoiceToOnlineDatabase() {
        mDb.child(googleUserAccountId).child("favTopics").setValue(getUserSelectedTopics()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                navigateToHomeActivity();
            }
        });
    }

    private void navigateToHomeActivity() {
        Intent homeActivityIntent = HomeActivity.getIntentToNavigate(this);
        startActivity(homeActivityIntent);
    }
}