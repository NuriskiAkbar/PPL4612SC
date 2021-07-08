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
import com.riquest.sistemmanajemenmenu.MakananAdapter
import com.riquest.sistemmanajemenmenu.R
import com.riquest.sistemmanajemenmenu.home.DashboardMenu
import com.riquest.sistemmanajemenmenu.model.Food
import com.riquest.sistemmanajemenmenu.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.tv_nama
import kotlinx.android.synthetic.main.fragment_dashboard.tv_no_meja
import kotlinx.android.synthetic.main.fragment_food.*


/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment : Fragment() {

    private lateinit var preferences : Preferences
    private lateinit var mDatabase3: DatabaseReference
    private var dataList3 = ArrayList<Food>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase3 = FirebaseDatabase.getInstance().getReference("Food")
        tv_nama.setText(preferences.getValues("nama"))
        tv_no_meja.setText(preferences.getValues("nomeja"))

        rv_makanan.layoutManager = LinearLayoutManager(context!!.applicationContext)
        getData3()

    }

   private fun getData3() {
        mDatabase3.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList3.clear()
                for(getdataSnapshot in dataSnapshot.children){
                    var listmakanan = getdataSnapshot.getValue(Food::class.java)
                    dataList3.add(listmakanan!!)
                }

                rv_makanan.adapter = MakananAdapter(dataList3) {
                    food ->
                    (activity as DashboardMenu).mList.add(food)
                    (activity as DashboardMenu).setTotalPesanan(1)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }
        })
        }
    }