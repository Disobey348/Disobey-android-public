package com.example.disobey.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.disobey.LeaderboardsFirestoreAdapter
import com.example.disobey.LeaderboardsUserData
import com.example.disobey.R
import com.google.firebase.firestore.FirebaseFirestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    lateinit var LeaderboardView: View

    lateinit var fsrecyclerview: RecyclerView
    lateinit var pref: SharedPreferences
    private var disobeySteps = 0
    var userList = ArrayList<LeaderboardsUserData>()
    var fsadapter= LeaderboardsFirestoreAdapter(userList)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        LeaderboardView=inflater.inflate(R.layout.fragment_leaderboard, container, false)

        fsrecyclerview=LeaderboardView.findViewById<RecyclerView>(R.id.leaderboardsRecycler)
        fsrecyclerview.layoutManager = LinearLayoutManager(context)

        val db = FirebaseFirestore.getInstance()
        var documentRef=db.collection("leaderboards").document("hyderabad")
        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val leaderboardData = documentSnapshot.data
//                    println(leaderboardData)
//                    println(leaderboardData?.javaClass)
                    if (leaderboardData != null) {
                        for ((_, userData) in leaderboardData) {
                            println(userData.javaClass)
                            var temp = userData as HashMap<String?, Any?>
                            var coin=temp.get("disobeySteps").toString().toInt()
                            var name=temp.get("name").toString()
                            userList.add(LeaderboardsUserData(name,coin))
//                            fsadapter.notifyDataSetChanged()
//                            println(leaderboardList)
//                            println(""+coin?.javaClass + " "+ name?.javaClass)
                        }
                    }
//                    for ((index, entry) in userList.withIndex()) {
//                        val rank = index + 1
//                        val name = entry.name
//                        val coins = entry.disobeySteps
//                        println("Rank $rank: $name - $coins coins")
//                    }

                    // Sort the leaderboard list based on coins (descending order)
                    userList.sortByDescending { it.disobeySteps }
                    fsadapter.notifyDataSetChanged()

                } else {
                    println("Document doesn't exist")
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting document: $exception")
            }

        fsrecyclerview.adapter=fsadapter


        return LeaderboardView;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}