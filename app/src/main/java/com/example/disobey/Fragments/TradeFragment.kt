package com.example.disobey.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.example.disobey.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TradeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TradeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_trade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.enter).setOnClickListener{
//            val alertDialogBuilder = AlertDialog.Builder(context)
//            alertDialogBuilder.setMessage("Event is live: Walk, collect and earn to ace the leaderboards\nRaffle entry for top 100 players to win exclusive Nike Sneaker worth Rs 12K")
//            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
//                dialog.dismiss()
//            }
//            val alertDialog = alertDialogBuilder.create()
//            alertDialog.show()
            var dialog= Dialog(requireContext())
            dialog.setContentView(R.layout.hurray)
            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            dialog.findViewById<ImageButton>(R.id.close).setOnClickListener {
                dialog.dismiss()
            }
            dialog.findViewById<Button>(R.id.collect).setOnClickListener {
                dialog.dismiss()
            }
            dialog.findViewById<TextView>(R.id.t1).text="Event is live"
            dialog.findViewById<LottieAnimationView>(R.id.animationView).visibility=View.GONE
            dialog.findViewById<ImageView>(R.id.reward).visibility=View.GONE
            dialog.findViewById<TextView>(R.id.t2).text="Walk, collect and earn to ace the leaderboards\n\nRaffle entry for top 100 players to win exclusive Nike Sneaker worth Rs 12K\n"
            dialog.findViewById<Button>(R.id.collect).text="Ok"
            dialog.show()
        }
        view.findViewById<Button>(R.id.coming).setOnClickListener{
//            val alertDialogBuilder = AlertDialog.Builder(context)
//            alertDialogBuilder.setMessage("Stay tuned for our upcoming tournament and get a chance to win Nike lost and found worth Rs 50k")
//            alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
//                dialog.dismiss()
//            }
//            val alertDialog = alertDialogBuilder.create()
//            alertDialog.show()

            var dialog= Dialog(requireContext())
            dialog.setContentView(R.layout.hurray)
            dialog.findViewById<TextView>(R.id.t1).text="Stay tuned for our upcoming tournament"
            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
            dialog.findViewById<ImageButton>(R.id.close).setOnClickListener {
                dialog.dismiss()
            }
            dialog.findViewById<Button>(R.id.collect).setOnClickListener {
                dialog.dismiss()
            }
            dialog.findViewById<LottieAnimationView>(R.id.animationView).visibility=View.GONE
            dialog.findViewById<ImageView>(R.id.reward).visibility=View.GONE
            dialog.findViewById<TextView>(R.id.t2).text="Get a chance to win Nike Lost and Found worth Rs 50k\n"
            dialog.findViewById<Button>(R.id.collect).text="Ok"
            dialog.show()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TradeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TradeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}