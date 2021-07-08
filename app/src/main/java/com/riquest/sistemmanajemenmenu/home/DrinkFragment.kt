package com.riquest.sistemmanajemenmenu.home.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.riquest.sistemmanajemenmenu.MinumanAdapter
import com.riquest.sistemmanajemenmenu.R
import com.riquest.sistemmanajemenmenu.home.DashboardMenu
import com.riquest.sistemmanajemenmenu.model.Drink
import com.riquest.sistemmanajemenmenu.model.Food
import com.riquest.sistemmanajemenmenu.utils.Preferences
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.tv_nama
import kotlinx.android.synthetic.main.fragment_dashboard.tv_no_meja
import kotlinx.android.synthetic.main.fragment_drink.*
import kotlinx.android.synthetic.main.fragment_food.*


/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrinkFragment : Fragment() {

    private lateinit var preferences : Preferences
    private lateinit var mDatabase4 : DatabaseReference
    private var dataList4 = ArrayList<Food>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase4 = FirebaseDatabase.getInstance().getReference("Drink")


        tv_nama.setText(preferences.getValues("nama"))
        tv_no_meja.setText(preferences.getValues("nomeja"))

        rv_minuman.layoutManager = LinearLayoutManager(context!!.applicationContext)

        getData4()

    }

    private fun getData4() {
        mDatabase4.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList4.clear()
                for(getdataSnapshot in dataSnapshot.children){
                    var listminuman = getdataSnapshot.getValue(Drink::class.java)
                    dataList4.add(Food(listminuman?.namamin,listminuman?.harga,listminuman?.harga2,listminuman?.tag1,listminuman?.tag2,listminuman?.poster, listminuman?.qty))
                }

                rv_minuman.adapter = MinumanAdapter(dataList4) {
                    drink->
                    (activity as DashboardMenu).mList.add(drink)
                    (activity as DashboardMenu).setTotalPesanan(1)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}