package com.collabapps.moca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAuthActivity extends AppCompatActivity {

    private GoogleSignInAccount account;
    private DatabaseReference mDb = FirebaseDatabase.getInstance().getReference(DATABASE_NAME);

    private static final int GOOGLE_SIGN_IN = 1;
    public static final String DATABASE_NAME = "users";

    private GoogleSignInClient mGoogleSignInClient;

    public static Intent getIntentToNavigate(Context context) {
        Intent userAuthActivityIntent = new Intent(context, UserAuthActivity.class);
        userAuthActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return userAuthActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        initGoogleSignInOptions();
        setupGoogleSignInButton();
    }

    private void initGoogleSignInOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(getString(R.string.web_client_id))
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
                persistUserDataToOnlineDatabase(account);
            }
        } catch (ApiException e) {
            toastErrorMessageWithStatusCode(e.getStatusCode());
        }
    }

    private void persistUserDataToOnlineDatabase(GoogleSignInAccount account) {
        User user = new User(account.getId(), account.getDisplayName(), account.getEmail());
        mDb.child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                navigateToChooseTopicsActivity();
            }
        });
    }

    private void navigateToChooseTopicsActivity() {
        Intent chooseTopicsActivityIntent = ChooseTopicsActivity.getIntentToNavigate(this, account.getId());
        startActivity(chooseTopicsActivityIntent);
    }



    private void toastErrorMessageWithStatusCode(int exceptionStatusCode) {
        Toast.makeText(this, "signInResult:failed code=" + exceptionStatusCode, Toast.LENGTH_SHORT).show();
    }
}