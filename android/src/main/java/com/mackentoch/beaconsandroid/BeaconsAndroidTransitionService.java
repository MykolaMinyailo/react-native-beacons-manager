package com.mackentoch.beaconsandroid;

import android.app.ForegroundServiceStartNotAllowedException;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import androidx.annotation.Nullable;

import android.os.Build;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import static com.mackentoch.beaconsandroid.BeaconsAndroidModule.LOG_TAG;

public class BeaconsAndroidTransitionService extends HeadlessJsTaskService {
    @Override
    public void onCreate() {
        super.onCreate();

        Context mContext = this.getApplicationContext();
        Resources res = mContext.getResources();
        String packageName = mContext.getPackageName();
        String CHANNEL_ID = "rn-push-notification-channel-id";
        String CHANNEL_NAME = res.getString(res.getIdentifier("notification_channel_name", "string", packageName));
        String CHANNEL_DESCRIPTION = res.getString(res.getIdentifier("notification_channel_description", "string", packageName));

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        channel.setDescription(CHANNEL_DESCRIPTION);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

        Notification notification =
                new Notification.Builder(mContext, CHANNEL_ID)
                        .setContentTitle("")
                        .setContentText("")
                        .setSmallIcon(res.getIdentifier("ic_notification", "mipmap", packageName))
                        .build();

        try {
            startForeground(2, notification);
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && (e instanceof ForegroundServiceStartNotAllowedException || e instanceof SecurityException)) {
                Log.d(LOG_TAG, "BeaconsAndroidTransitionService skip Error: ForegroundServiceStartNotAllowedException or SecurityException");
            } else {
                throw e;
            }
        }
    }

    @Override
    @Nullable
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent)
    {
        Log.d(LOG_TAG, "BeaconsAndroidTransitionService START...");

        WritableMap jsArgs = Arguments.createMap();
        return new HeadlessJsTaskConfig(BeaconsAndroidModule.TRANSITION_TASK_NAME, jsArgs, 0, true);
    }
}
