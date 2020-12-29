package com.collabapps.moca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.collabapps.moca.adapters.EventPagerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

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

        if (account == null) {
            navigateToUserAuthActivity();
        }

        initToolbar();
        initTabLayoutWithViewPager();
        initAddEventFab();
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

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    private void initAddEventFab() {
        //TODO: Navigate to AddNewEventActivity
    }

    private void initGoogleSignInOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void initTabLayoutWithViewPager() {
        ViewPager viewPager = findViewById(R.id.event_fragments_viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);
        setupViewPagerAdapter(viewPager);

        final int[] tabIconDrawables = {R.drawable.ic_interests, R.drawable.ic_recommended};
        setTabIcons(tabLayout, tabIconDrawables);
    }

    private void setTabIcons(TabLayout t, int[] tabIconDrawables) {
        for (int currentTab = 0; currentTab < t.getTabCount(); currentTab++) {
            t.getTabAt(currentTab).setIcon(tabIconDrawables[currentTab]);
        }
    }

    private void setupViewPagerAdapter(ViewPager viewPager) {
        EventPagerAdapter eventPagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), 0);
        eventPagerAdapter.addFragment(new InterestEventsFragment(), getString(R.string.tab_item_interests));
        eventPagerAdapter.addFragment(new RecommendedEventsFragment(), getString(R.string.tab_item_recommended));
        viewPager.setAdapter(eventPagerAdapter);
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