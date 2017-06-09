package com.example.katevandonge.dejaphotoproject;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getContext;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Peter on 6/7/2017.
 */

public class FirebaseTest {

    @Rule
    public ActivityTestRule<FriendActivity  > friendActivity = new ActivityTestRule<FriendActivity>(FriendActivity.class);

    // Tests the method that keeps track of the user's location and the string of the address
    @Test
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

    }

}
