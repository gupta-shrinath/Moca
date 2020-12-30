package com.collabapps.moca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.collabapps.moca.data.User;
import com.collabapps.moca.util.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserAuthActivity extends AppCompatActivity {

    private static final String TAG = "UserAuthActivity";

    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private DatabaseReference mDb = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_NAME);

    private static final int GOOGLE_SIGN_IN = 1;

    public static Intent getIntentToNavigate(Context context) {
        Intent userAuthActivityIntent = new Intent(context, UserAuthActivity.class);
        userAuthActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return userAuthActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        initProgressBar();
        initGoogleSignInOptions();
        setupGoogleSignInButton();
    }

    private void initGoogleSignInOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void setupGoogleSignInButton() {
        findViewById(R.id.google_signin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            if (account != null) {
                User user = new User(account.getId(), account.getDisplayName(), account.getEmail());
                handleUserDataPersistence(user);
            }
        } catch (ApiException e) {
            Log.d(TAG, "handleSignInResult: " + e.printStackTrace());
        }
    }

    private void handleUserDataPersistence(User user) {
        showProgressBar();

        Query userCheckQuery = mDb.child(user.getId());
        userCheckQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hideProgressBar();
                if (!snapshot.exists()) {
                    persistUserDataToOnlineDatabase(user);
                } else {
                    handleUserNavigation(user.getId());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void persistUserDataToOnlineDatabase(User user) {
        mDb.child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                handleUserNavigation(user.getId());
            }
        });

        hideProgressBar();
    }

    private void navigateToChooseTopicsActivity() {
        Intent chooseTopicsActivityIntent = ChooseTopicsActivity.getIntentToNavigate(this, account.getId());
        startActivity(chooseTopicsActivityIntent);
    }

    private void navigateToHomeActivity() {
        Intent homeActivityIntent = HomeActivity.getIntentToNavigate(this);
        startActivity(homeActivityIntent);
    }

    private void handleUserNavigation(String googleAccountId) {
        showProgressBar();

        Query isFirstTimeQuery = mDb.child(googleAccountId).child(Constants.DATABASE_CHILD_FAV_TOPICS);
        isFirstTimeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hideProgressBar();

                if (!snapshot.exists()) {
                    Toast.makeText(UserAuthActivity.this, "User is new", Toast.LENGTH_SHORT).show();
                    navigateToChooseTopicsActivity();
                } else {
                    Toast.makeText(UserAuthActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    navigateToHomeActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressBar();
            }
        });
    }

    private void initProgressBar() {
        progressBar = findViewById(R.id.progress_bar);
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void toastErrorMessageWithStatusCode(int exceptionStatusCode) {
        Toast.makeText(this, "signInResult:failed code=" + exceptionStatusCode, Toast.LENGTH_SHORT).show();
    }
}