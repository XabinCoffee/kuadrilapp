package com.kapp.rxabin.kuadrilapp;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FrontpageActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    private Button mSignOut;
    private Fragment mCurrentFragment;
    private PrefFragment pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();


        //TODO Konpontzeko, settings-etik irten ondoren NullPointer???
        if (getIntent().getBooleanExtra("settings",false)){

            navigation.setSelectedItemId(R.id.nav_settings);
        } else {

            navigation.setSelectedItemId(R.id.nav_events);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_events:

                if (pf!=null) getFragmentManager().beginTransaction().hide(pf).commit();
                switchContent(new EventsFragment());

                return true;
            case R.id.nav_new:

                if (pf!=null) getFragmentManager().beginTransaction().hide(pf).commit();
                switchContent(new CreateEventFragment());

                return true;
            case R.id.nav_settings:

                getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).commit();
                pf = new PrefFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, pf).commit();

                return true;

        }
        return false;
    }

    public void switchContent(Fragment fragment) {
        mCurrentFragment = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .commit();

    }

   // @Override
    /*public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }*/


    private void signOut() {
        mAuth.signOut();
        //updateUI(null);
    }
}
