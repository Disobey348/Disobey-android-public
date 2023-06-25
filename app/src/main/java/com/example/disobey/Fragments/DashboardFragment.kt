package com.example.disobey.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.disobey.Avatar
import com.example.disobey.Backpack
import com.example.disobey.R
import com.example.disobey.Stats
import com.example.disobey.snapCam
import com.ismaeldivita.chipnavigation.ChipNavigationBar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
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

    lateinit var chipNavigationBar : ChipNavigationBar;
    lateinit var DashboardView: View
    lateinit var avatarButton: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        DashboardView= inflater.inflate(R.layout.fragment_dashboard, container, false)
        return DashboardView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipNavigationBar = view.findViewById(R.id.nav)
        avatarButton=view.findViewById(R.id.avatar1)
        avatarButton.setOnClickListener{
            val avatarIntent = Intent(context, Avatar::class.java)
            startActivity(avatarIntent)
        }
        view.findViewById<ImageButton>(R.id.avatar2).setOnClickListener{
            val avatarIntent = Intent(context, Avatar::class.java)
            startActivity(avatarIntent)
        }
        chipNavigationBar.setItemSelected(
            R.id.bottom_nav_backpack,
            true
        )
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.secondary_fragment_container,
                Backpack()
            ).commit()
        bottomMenu()
    }
    private fun bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener { id ->
            val fragment: Fragment = when (id) {
                R.id.bottom_nav_stats -> Stats()
                R.id.bottom_nav_backpack -> Backpack()
                else -> throw IllegalArgumentException("Invalid menu item ID")
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.secondary_fragment_container, fragment)
                .commit()
        }
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment DashboardFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            DashboardFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}