package com.example.disobey

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Backpack.newInstance] factory method to
 * create an instance of this fragment.
 */
class Backpack : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val db = FirebaseFirestore.getInstance()
    lateinit var fsrecyclerview: RecyclerView
    lateinit var pref: SharedPreferences
    lateinit var backpackSneakerList :ArrayList<SneakerDataStruc>
    var sneakerCountMap = hashMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pref = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_bagpack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        backpackSneakerList=ArrayList()
//        todo: initial map count trial code kept for reference
//        val docRef = db.collection("userData").document(user!!.uid).collection("backpack").document("count")
//        docRef.get().addOnSuccessListener { documentSnapshot ->
//            if (documentSnapshot.exists()) {
//                val documentData = documentSnapshot.data
//                // Parse the document into a map
//                val sneakerCounts = documentData ?: mapOf()
//
//                // Access the count of a specific sneaker
//                val countSneakerB = sneakerCounts["B"] ?: 0
//                val countSneakerA = sneakerCounts["A"] ?: 0
//                println("Count of a: $countSneakerA")
//                println("Count of b: $countSneakerB")
//            } else {
//                println("Document not found.")
//            }
//        }.addOnFailureListener { exception ->
//            println("Error getting document: $exception")
//        }
        // Retrieve the JSON string from SharedPreferences
        val sneakerMapJson = pref.getString("sneakerCountMap", null)
        val backpackSneakerListJson = pref.getString("backpackSneakerList", null)
        val gson = Gson()
        if(sneakerMapJson!=null){
            sneakerCountMap = gson.fromJson(sneakerMapJson, object : TypeToken<HashMap<String, Int>>() {}.type)
//            println(sneakerCountMap)
        }
        else{
//            println("no sneaker map yet")
        }
        if(backpackSneakerListJson!=null){
            backpackSneakerList = gson.fromJson(backpackSneakerListJson, object : TypeToken<ArrayList<SneakerDataStruc>>() {}.type)
//            println(backpackSneakerList)
        }
        else{
//            println("no sneakers list yet")
        }
        fsrecyclerview=view.findViewById<RecyclerView>(R.id.backpackGrid)
        fsrecyclerview.layoutManager = GridLayoutManager(context,2)
//        var mySneakerList = ArrayList<SneakerDataStruc>()
//        val msneaker=SneakerData()
//        GlobalScope.launch(Dispatchers.Main) {
//            val sneakerListDeferred = msneaker.firestoreRetrieve()
//            mySneakerList = sneakerListDeferred.await()
            var fsadapter= BackpackAdapter(backpackSneakerList,sneakerCountMap,1)
            fsrecyclerview.adapter=fsadapter
//        }
//        mySneakerList.add(SneakerDataStruc("test","epic","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/clownKickers.png?alt=media&token=6fcf0956-f614-428b-8d9e-c69de11beafa",10))
//        mySneakerList.add(SneakerDataStruc("test","epic","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/glitchStep.png?alt=media&token=d04dc918-1cf5-44b7-9273-c0f5b1ed2719",10))
//        mySneakerList.add(SneakerDataStruc("test","epic","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/loneShark.png?alt=media&token=dbf4fa1b-11d7-486c-90bf-1307ecf09c9c",10))
//        mySneakerList.add(SneakerDataStruc("test","epic","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/predatoryCobra.png?alt=media&token=91c450a0-1b86-4bcd-84ab-e58f7622d812",10))
//        val list = arrayListOf<SneakerDataStruc>()
//        db.collection("sneakers").document("legendary").get()
//            .addOnSuccessListener { document ->
//                if (document != null && document.exists()) {
//                    list.addAll(document.toObject(SneakerData::class.java)!!.sneakerList)
//
//                } else {
//                    Log.d("Firestore", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.e("Firestore", "Error getting document: $exception")
//            }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Bagpack.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Backpack().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}