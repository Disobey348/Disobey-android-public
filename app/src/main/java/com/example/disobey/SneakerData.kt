package com.example.disobey

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Collections
import java.util.Random
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


data class SneakerDataStruc(val name:String="", val type:String="", val image:String="",  val coin:Int=0)

class SneakerData {
    var sneakerData = ArrayList<SneakerDataStruc>()
    var threeDList = ArrayList<SneakerDataStruc>()
    var commonList = ArrayList<SneakerDataStruc>()
    var rareList = ArrayList<SneakerDataStruc>()
    var epicList = ArrayList<SneakerDataStruc>()
    var legendaryList = ArrayList<SneakerDataStruc>()
    var sneakerList = ArrayList<SneakerDataStruc>()
    val db = FirebaseFirestore.getInstance()
    fun firestoreRetrieve(): Deferred<ArrayList<SneakerDataStruc>>{
        return GlobalScope.async(Dispatchers.IO) {
            val threeDTask = async { getDocument("threeD") }
            val commonTask = async { getDocument("common") }
            val rareTask = async { getDocument("rare") }
            val epicTask = async { getDocument("epic") }
            val legendaryTask = async { getDocument("legendary") }

            threeDList = threeDTask.await()
            commonList = commonTask.await()
            rareList = rareTask.await()
            epicList = epicTask.await()
            legendaryList = legendaryTask.await()

            // Continue with the next part of your code here
            createSneakerList(threeDList,2)
            createSneakerList(commonList,30)
            createSneakerList(rareList,8)
            createSneakerList(epicList,8)
            createSneakerList(legendaryList,2)
            sneakerList
        }

    }
    suspend fun getDocument(documentName: String): ArrayList<SneakerDataStruc> {
        val list = arrayListOf<SneakerDataStruc>()
        val task = db.collection("sneakers").document(documentName).get()
        try {
            val document = task.await()
            if (document.exists()) {
                list.addAll(document.toObject(SneakerData::class.java)!!.sneakerData)
            }
        } catch (e: Exception) {
            // Handle any exceptions that occur during the retrieval of the document
        }
        return list
    }
//    fun populateMarkers(){
//        firestoreRetrieve()
////        not working as retrieval in firestore is asynchronous hencce blank lists are being passed even when kotlin is sycnhronous
//        println(commonList)
//        createSneakerList(commonList,37)
//        createSneakerList(rareList,8)
//        createSneakerList(epicList,4)
//        createSneakerList(legendaryList,1)
//        println(sneakerList)
////        return sneakerList
//    }
    fun createSneakerList(inputList:ArrayList<SneakerDataStruc>,count:Int){
        println(inputList.size)
        sneakerList.addAll(List(count){inputList[Random().nextInt(inputList.size)]})
        println(sneakerList.size)
    }
}
