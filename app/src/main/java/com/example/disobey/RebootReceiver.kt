package com.example.disobey

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService

class RebootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            println("Hello, Msg from Disobey, Device Rebooted")
            var pref=context?.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            val myEdit = pref?.edit()
            myEdit?.putInt("initialSteps",0)
            myEdit?.apply()
            val alarmy = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var triggerTime = System.currentTimeMillis() + (5 * 60 + 30) * 60 * 1000
//            India is 5 hr 30 mins ahead so added it for IST conversion
//            subtracting the remainder ((triggerTime)%(60*60*1000)) converts to to nearest hour

            triggerTime=(((24*60+1)*60*1000)-(triggerTime% (24*60*60*1000)))+triggerTime- ((5 * 60 + 30) * 60 * 1000)
            println(triggerTime)

            val broadcast = Intent(context, dailyReceiver::class.java)
            val pi =
                PendingIntent.getBroadcast(context, 100, broadcast, PendingIntent.FLAG_MUTABLE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmy.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pi)
            }
        }
    }
}