package com.mackentoch.beaconsandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.mackentoch.beaconsandroid.BeaconsAndroidModule.LOG_TAG;

public class BeaconsAndroidBootReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.d(LOG_TAG, "onReceive...");

		String action = intent.getAction();
		if (action != null && action.equals("android.intent.action.BOOT_COMPLETED")) {
			context.startForegroundService(new Intent(context, BeaconsAndroidTransitionService.class));
		}
	}
}
