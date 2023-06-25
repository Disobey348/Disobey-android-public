package com.example.disobey

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.time.LocalDate

class midnightService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        TODO("Return the communication channel to the service.")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //        providing a constant notification while service is running
//        without this notification pop up is force closed by system os

        val user = FirebaseAuth.getInstance().currentUser
        val db = FirebaseFirestore.getInstance()
        var pref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val myEdit = pref.edit()
        var disobeyCoins= pref.getFloat("disobeyCoins",0F)
        var dailyCoins= pref.getFloat("dailyCoins",0F)
        var disobeySteps= pref.getInt("disobeySteps",0)
        var dailySteps= pref.getInt("dailySteps",0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "com.example.floatinglayout",
                "Floating Layout Service",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.lightColor = Color.BLUE
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val notificationManager =
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)!!
            notificationManager!!.createNotificationChannel(channel)
            val builder = NotificationCompat.Builder(this, "com.example.floatinglayout")
            val notification = builder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Disobey - midnight updates")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build()
            startForeground(2, notification)
        }
        val currentDate = LocalDate.now().toString()
        val id = db.collection("leaderboards").document("hyderabad")
        val data= hashMapOf(user!!.uid to LeaderboardsUserData(user!!.displayName,disobeyCoins.toInt()))
        id.set(data,SetOptions.merge())
//        id.get().addOnCompleteListener { data ->
//            if (data.isSuccessful) {
//                val document = data.result
//                if (document.exists()) {
////                    id.update("disobeySteps",disobeySteps)
//
////                    Log.i("TAG", "Leaderboard Document exists!")
//                } else {
////                    Log.i("TAG", "Leaderboard Document does not exist!")
////                    val docData = hashMapOf(
////                        "name" to user.displayName,
////                        "disobeySteps" to disobeySteps
////                    )
////                    id.set(docData)
//                }
//            } else {
//                Log.i("TAG", "Leaderboard Failed with: ", data.exception)
//            }
//        }

        myEdit.putInt("dailySteps",0)
        myEdit.putFloat("dailyCoins",0F)
        myEdit.putString("leaderboardUpdateDate", currentDate)
        myEdit.apply()

        dailySteps= pref.getInt("dailySteps",0)

        println("detected, steps reset ~ Disobey")


        Toast.makeText(this, "daily steps reset to $dailySteps", Toast.LENGTH_SHORT).show()
        stopForeground(true)
        stopSelf()

        return START_NOT_STICKY
    }
}