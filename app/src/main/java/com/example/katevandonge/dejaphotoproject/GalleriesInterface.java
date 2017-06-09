package com.example.katevandonge.dejaphotoproject;

import android.net.Uri;

import java.util.PriorityQueue;

/**
 * Created by katevandonge on 6/9/17.
 */

interface GalleriesInterface {
    public void addPhoto(Uri uri);

    public PriorityQueue<Photo> getPQ();

}
