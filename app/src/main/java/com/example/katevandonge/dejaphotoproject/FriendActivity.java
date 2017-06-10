package com.example.katevandonge.dejaphotoproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
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

    User myUser;    // class variable to manipulate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize user
        myUser = new User();

        // Set button to go back to main menu
        Button switchScreen= (Button) findViewById(R.id.FriendBack);
        switchScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    /*  Add friend to myUser on Firebase
     *
     */
    public void addFriend(View button) throws InterruptedException {
        // Check if user is logged in, else do nothing
        if( !myUser.isLoggedIn()){
            Toast.makeText(getApplicationContext(), "Please login / register", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.i("FriendActivity", myUser.getId());

        // Get the email of the user we want to add
        EditText etEmail = (EditText) findViewById(R.id.UserEmailSearch);
        String friendEmail = etEmail.getText().toString();

        // Connect to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();

        // Go to the users directory
        DatabaseReference userRef = myFirebaseRef.child("users/"+myUser.getId());
        // Go to this myUser's friend directory
        DatabaseReference myFriendsRef = userRef.child("myFriends");

        // Testing user friends size is correct
        ArrayList<String> userFriendList = myUser.getFriends();
        Log.i("FriendActivity", ""+userFriendList.size());

        // Add the friends email to user's list of friends
        myFriendsRef.child(friendEmail.replaceAll("\\.","_")).setValue(friendEmail);


        // Dummy user to test Firebase functionality
        User testPhoto = new User();
        testPhoto.setEmail("phototest@gmail.com");

        //testPhoto.getFirebaseShareablePhoto();
        testPhoto.getFirebaseFriends();

    }

    /*  Registers a user if they don't exist yet, or logs in if they do
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
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
                            // Else do nothing and inform user
                            else{
                                myUser.setLoggedIn(false);
                                Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // do nothing
                        }
                    });


                }
                else {
                    // Access database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myFirebaseRef = database.getReference();
                    DatabaseReference userRef = myFirebaseRef.child("users");
                    userRef.child(myUser.getId()).setValue(myUser);

                    // Set the user to be logged in
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
