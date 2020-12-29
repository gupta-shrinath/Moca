package com.collabapps.moca.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.collabapps.moca.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {

    public static final int SIGNOUT_MENU_ITEM = R.id.signout;

    private GoogleSignInClient mGoogleSignInClient;

    public static Intent getIntentToNavigate(Context context) {
        Intent homeActivityIntent = new Intent(context, HomeActivity.class);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return homeActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initGoogleSignInOptions();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            //TODO: The User is signed in; use the account object and go to town!
        } else {
            navigateToUserAuthActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case SIGNOUT_MENU_ITEM:
                signUserOut();
                break;

            default:
                break;
        }
        return true;
    }

    private void initGoogleSignInOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signUserOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                navigateToUserAuthActivity();
            }
        });
    }

    private void navigateToUserAuthActivity() {
        Intent userAuthActivityIntent = UserAuthActivity.getIntentToNavigate(this);
        startActivity(userAuthActivityIntent);
    }
}