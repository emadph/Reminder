package co.pourahmadi.emad.Core.broadcast;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import co.pourahmadi.emad.Core.Statics.Constant;
import co.pourahmadi.emad.Core.customViews.NotificationHelper;

public class NotificationPublisher extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(Constant.ARG_STRING);
        String desc = intent.getStringExtra(Constant.ARG_STRING2);

        NotificationHelper notificationHelper = new NotificationHelper(context,title,desc);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
        Notification notification = nb.build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;
    }
}