package com.example.katevandonge.dejaphotoproject;

import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetLocation extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        //String locName= Photo.locName;
        //WallpaperManager myWall = Wall.myWall;
        //Photo[] wallArr = Wall.photoArr;
        //int counter = Wall.counter;


        //BUT THIS IS NOT FOR CURRENT PHOTO
        //Photo mine = Gallery.queueCopy.peek();
        //String otherS = "null";//mine.locName;
        //Log.v(otherS, otherS);
        //String locName = wallArr[counter].locName;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_location);
        //views.setTextViewText(R.id.appwidget_text2, "");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //String locName= Photo.locName;
            //WallpaperManager myWall = Wall.myWall;
            //Photo[] wallArr = Wall.photoArr;
            //int counter = Wall.counter;
//            Photo myPhoto = Wall.photoArr[Wall.counter]
//          String locName = myPhoto.locName;
            //Log.v("locName", "locName");
   //         RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_location);
     //       views.setTextViewText(R.id.appwidget_text2, "no");

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

