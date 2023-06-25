package com.example.disobey.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.disobey.BackpackAdapter
import com.example.disobey.R
import com.example.disobey.SneakerDataStruc
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
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    lateinit var fsrecyclerview: RecyclerView
    lateinit var pref: SharedPreferences
    lateinit var threeDSneakerList :ArrayList<SneakerDataStruc>
    var sneakerCountMap = hashMapOf<String, Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pref = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser
        threeDSneakerList=ArrayList()

        val sneakerMapJson = pref.getString("sneakerCountMap", null)
        val threeDSneakerListJson = pref.getString("threeDSneakerList", null)
        val gson = Gson()
        if(sneakerMapJson!=null){
            sneakerCountMap = gson.fromJson(sneakerMapJson, object : TypeToken<HashMap<String, Int>>() {}.type)
//            println(sneakerCountMap)
        }
        else{
//            println("no sneaker map yet")
        }
        if(threeDSneakerListJson!=null){
            threeDSneakerList = gson.fromJson(threeDSneakerListJson, object : TypeToken<ArrayList<SneakerDataStruc>>() {}.type)
//            println(backpackSneakerList)
        }
        else{
//            println("no sneakers list yet")
        }

        fsrecyclerview=view.findViewById<RecyclerView>(R.id.ThreeDGrid)
        fsrecyclerview.layoutManager = GridLayoutManager(context,2)

//        threeDSneakerList.add(SneakerDataStruc("Coloured Sneaker","182d9586-9a63-4ae4-8b73-ce8d10052422","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/coloredSneaker.png?alt=media&token=c88ca2f4-ba0f-4450-9096-ed2db6481f22",10))
//        threeDSneakerList.add(SneakerDataStruc("90's Nostalgia","d36f63db-23f1-457b-8059-3914de823243","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/90sNostalgia.png?alt=media&token=0e2dfaf3-2444-41de-98cd-48665aafc0aa",10))
//        threeDSneakerList.add(SneakerDataStruc("Black Gold","0295c23b-aad6-40c4-8b89-689e45a97e61","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/goldBlackSneaker.png?alt=media&token=767b9a25-72d8-4045-9586-242d63b3144c",10))
//        threeDSneakerList.add(SneakerDataStruc("Hyper Sneaker","32960919-ddaf-46ab-b4ba-724097569166","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/hyperSneaker.png?alt=media&token=c65d7f10-6a4a-4bed-baeb-2761feef136e",10))
//        threeDSneakerList.add(SneakerDataStruc("Cyberpunk Sneaker","c75cfb4e-c15f-4570-a8df-b8e229c2e6d7","https://firebasestorage.googleapis.com/v0/b/disobey-790c8.appspot.com/o/cyberpunk.png?alt=media&token=63bec4a0-1b4f-44d8-8a55-9f797ff8a395",10))

        var fsadapter= BackpackAdapter(threeDSneakerList,sneakerCountMap,2)
        fsrecyclerview.adapter=fsadapter
//        }
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
         * @return A new instance of fragment CameraFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CameraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}