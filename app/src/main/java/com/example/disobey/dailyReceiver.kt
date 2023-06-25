package com.example.disobey

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build

class dailyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmy = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//      System.currentTimeMillis() return time of device in mili second since a reference known as the UNIX epoch: 1970-01-01 00:00:00 UTC

//      System.currentTimeMillis() return time of device in mili second since a reference known as the UNIX epoch: 1970-01-01 00:00:00 UTC
        var triggerTime = System.currentTimeMillis() + (5 * 60 + 30) * 60 * 1000
        triggerTime=(((24*60+1)*60*1000)-(triggerTime% (24*60*60*1000)))+triggerTime- ((5 * 60 + 30) * 60 * 1000)
//        println(triggerTime)
//        if (triggerTime - triggerTime % (60 * 60 * 1000) % (24 * 60 * 60 * 1000) == 0L) {
//            triggerTime =
//                triggerTime - triggerTime % (60 * 60 * 1000) + 9 * 60 * 60 * 1000 - (5 * 60 + 30) * 60 * 1000
//        } else if (triggerTime - triggerTime % (60 * 60 * 1000) % (24 * 60 * 60 * 1000) > 8) {
//            triggerTime =
//                triggerTime - triggerTime % (60 * 60 * 1000) + 61 * 60 * 1000 - (5 * 60 + 30) * 60 * 1000
//        }
        val broadcast = Intent(context, dailyReceiver::class.java)
        val pi = PendingIntent.getBroadcast(context, 100, broadcast, PendingIntent.FLAG_MUTABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmy.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pi)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(Intent(context, midnightService::class.java))
        } else {
            context!!.startService(Intent(context, midnightService::class.java))
        }
    }
}