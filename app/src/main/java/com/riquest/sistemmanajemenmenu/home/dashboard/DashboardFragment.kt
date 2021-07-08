package com.riquest.sistemmanajemenmenu.home.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.riquest.sistemmanajemenmenu.R
import com.riquest.sistemmanajemenmenu.home.DashboardMenu
import com.riquest.sistemmanajemenmenu.model.Food
import com.riquest.sistemmanajemenmenu.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var preferences : Preferences
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mDatabase2: DatabaseReference
    private var dataList = ArrayList<Food>()
    private var dataList2 = ArrayList<Food>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Newmenu")
        mDatabase2 = FirebaseDatabase.getInstance().getReference("Palingdipesan")

        tv_nama.setText(preferences.getValues("nama"))
        tv_no_meja.setText(preferences.getValues("nomeja"))

        rv_newmenu.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_palingdipesan.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        getData()
        getData2()
    }

    private fun getData2() {
        mDatabase2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList2.clear()
                for(getdataSnapshot in dataSnapshot.children){
                    var palingdipesan = getdataSnapshot.getValue(Food::class.java)
                    dataList2.add(palingdipesan!!)
                }

                rv_palingdipesan.adapter = PalingdipesanAdapter(dataList2) {
                    palingdipesan ->
                    (activity as DashboardMenu).mList.add(palingdipesan)
                    (activity as DashboardMenu).setTotalPesanan(1)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for(getdataSnapshot in dataSnapshot.children){
                    var newmenu = getdataSnapshot.getValue(Food::class.java)
                    dataList.add(newmenu!!)
                }

                rv_newmenu.adapter = NewmenuAdapter(dataList) {
                    newmenu ->
                    (activity as DashboardMenu).mList.add(newmenu)
                    (activity as DashboardMenu).setTotalPesanan(1)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }


}