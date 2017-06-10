package com.example.katevandonge.dejaphotoproject;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Peter on 6/7/2017.
 */

public class FirebaseTest {

    @Rule
    public ActivityTestRule<FriendActivity  > friendActivity = new ActivityTestRule<FriendActivity>(FriendActivity.class);

    // Tests if we can detect mutual friends
    // Relies on database being set up
   /* @Test
    public void testMutualFriends() throws InterruptedException {

        User test = new User();
        test.setEmail("testUser1@gmail.com");
        test.setPassword("password");

        User testFriend = new User();
        testFriend.setEmail("testUser2@gmail.com");
        testFriend.setPassword("password");

        assertEquals(true, test.checkMutualFriends(testFriend));


        User testNotFriend = new User();
        testNotFriend.setEmail("notAFriend@gmail.com");
        testNotFriend.setPassword("password");

        assertEquals(false, test.checkMutualFriends(testNotFriend));

    }*/

    //Test accessing friends list
    @Test
    public void testGetFriends(){
        User test = new User();
        test.setEmail("testUser1@gmail.com");
        test.setPassword("password");

        ArrayList<String> actualFriends = new ArrayList<>();
        actualFriends.add("friend1@gmail_com");
        actualFriends.add("friend2@gmail_com");
        actualFriends.add("testUser2@gmail_com");

        ArrayList<String> testFriends = test.getFirebaseFriends();

        for(int i = 0; i < testFriends.size(); i++){
            assertEquals(true, testFriends.get(i).equals(actualFriends.get(i)));
        }

    }
}
