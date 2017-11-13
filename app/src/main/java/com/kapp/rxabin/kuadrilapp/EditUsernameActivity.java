package com.kapp.rxabin.kuadrilapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.kapp.rxabin.kuadrilapp.database.DbManager;

public class EditUsernameActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_username);
        username = (EditText) findViewById(R.id.mName);
        mAuth = FirebaseAuth.getInstance();
    }


    public void updateName(View v){
        String name = username.getText().toString();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        DbManager.updateUser(currentUser.getUid(),name);
        finish();

    }


}