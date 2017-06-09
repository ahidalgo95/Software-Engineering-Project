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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.util.Pair;
import android.widget.Toast;

import java.net.URI;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Peter on 5/28/2017.
 */

public class FriendActivity extends AppCompatActivity {

    User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myUser = new User();

        Button switchScreen= (Button) findViewById(R.id.FriendBack);
        switchScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void addFriend(View button) throws InterruptedException {
        if( !myUser.isLoggedIn()){
            Toast.makeText(getApplicationContext(), "Please login / register", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i("FriendActivity", myUser.getId());
        EditText etEmail = (EditText) findViewById(R.id.UserEmailSearch);
        String friendEmail = etEmail.getText().toString();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference userRef = myFirebaseRef.child("users/"+myUser.getId());

        //myUser.addFriend(friendEmail);

        DatabaseReference myFriendsRef = userRef.child("myFriends");

        ArrayList<String> userFriendList = myUser.getFriends();
        Log.i("FriendActivity", ""+userFriendList.size());

        myFriendsRef.child(friendEmail.replaceAll("\\.","_")).setValue(friendEmail);

        User testPhoto = new User();
        testPhoto.setEmail("phototest@gmail.com");

        testPhoto.getFirebaseShareablePhoto();


        Log.i("ShareableFriendActivity", "size: " + testPhoto.myShareablePhotos.size());
        //Log.i("ShareableFriendActivity", "size2: " + testPhoto.getAL().size());


        //userRef.child("myFriends").setValue(myUser.getFriends());
        //userRef.push().setValue(myUser.getFriends());

    }

    public void submit(View button) {

        // Get the data the enter into Firebase
        EditText etName = (EditText) findViewById(R.id.myPassword);
        EditText etEmail = (EditText) findViewById(R.id.myEmail);
        String password = etName.getText().toString();
        String email = etEmail.getText().toString();


        // Create new user / login
        myUser = new User();
        myUser.setPassword(password);
        myUser.setEmail(email);

        // Tests firebase with dummy photo
        Uri test = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Photo temp = new Photo(getApplicationContext());
        temp.setUri(test);
        myUser.addPhotos(temp, getApplicationContext(), 1);

        Uri test2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Photo temp2 = new Photo(getApplicationContext());
        temp2.setUri(test2);
        myUser.addPhotos(temp2, getApplicationContext(), 2);



        // Accesses database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        DatabaseReference userRef = myFirebaseRef.child("users");

        Log.i("FriendActivity", myUser.getId());


        // Catches if the user already exists, if so, log in
        userRef.child(myUser.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // If data already exists "log in" the user
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myFirebaseRef = database.getReference();
                    DatabaseReference userRef = myFirebaseRef.child("users/"+ myUser.getId() + "/myPassword");

                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // check if password is right
                            EditText etName = (EditText) findViewById(R.id.myPassword);
                            String password = etName.getText().toString();
                            String storedPassword = dataSnapshot.getValue(String.class);
                            Log.i("FriendActivity", storedPassword + " " + password);
                            // If password is right log in
                            if(storedPassword.equals(password)) {
                                myUser.setLoggedIn(true);
                                Toast.makeText(getApplicationContext(), "User already exists! Logging in...", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                myUser.setLoggedIn(false);
                                Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
                else {
                    // Access database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myFirebaseRef = database.getReference();
                    DatabaseReference userRef = myFirebaseRef.child("users");
                    userRef.child(myUser.getId()).setValue(myUser);

                    myUser.setLoggedIn(true);


                    // Add user if not already in data base
                    userRef = myFirebaseRef.child("users/"+myUser.getId());
                    userRef.child("myFriends").setValue(myUser.getFriends());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // do nothing
            }
        });


        Intent output = new Intent();
        setResult(RESULT_OK, output);
    }



}
