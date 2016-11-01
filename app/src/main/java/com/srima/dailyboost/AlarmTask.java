package com.srima.dailyboost;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class  AlarmTask implements Runnable{

    private final Calendar date;
    private final AlarmManager am;
    private final Context context;

    public AlarmTask(Context context, Calendar date) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
    }

    @Override
    public void run() {

        Intent intent = new Intent(context, com.srima.dailyboost.NotifyService.class);
        intent.putExtra(com.srima.dailyboost.NotifyService.INTENT_NOTIFY, true);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
