package com.example.katevandonge.dejaphotoproject;

import android.content.BroadcastReceiver;

/**
 * Created by katevandonge on 5/10/17.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class NewAppWidgetIntentReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        /*if (intent.getAction().equals(NewAppWidget.WIDGET_BUTTON)) {
            updateWidgetListener(context);
        }*/
    }

   /* private void updateWidgetListener(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.new_app_widget);
        views.setOnClickPendingIntent(R.id.Karma, NewAppWidget.buildButtonPendingIntent(context));
        NewAppWidget.pushWidgetUpdate(context.getApplicationContext(),
                views);
    }*/
}
