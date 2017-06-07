package com.example.katevandonge.dejaphotoproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Peter on 5/28/2017.
 */

public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button switchScreen= (Button) findViewById(R.id.FriendBack);
        switchScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void submit(View button) {
        Log.i("test", "test");

        EditText etName = (EditText) findViewById(R.id.myName);
        EditText etEmail= (EditText) findViewById(R.id.myEmail);
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        Uri test = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        User thisUser = new User();
        thisUser.setName(name);
        thisUser.setEmail(email);

        Photo temp = new Photo(getApplicationContext());
        temp.setUri(test);
        thisUser.addPhotos(temp, getApplicationContext());
        //thisUser.addUri(test);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference userRef = myFirebaseRef.child("users");

        Log.i("FriendActivity", thisUser.getId());

        userRef.child(thisUser.getId()).setValue(thisUser);


        Intent output = new Intent();
        setResult(RESULT_OK, output);
    }


}
