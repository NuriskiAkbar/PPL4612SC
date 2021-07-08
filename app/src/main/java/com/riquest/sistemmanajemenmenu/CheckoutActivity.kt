package com.riquest.sistemmanajemenmenu

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.riquest.sistemmanajemenmenu.model.Food
import com.riquest.sistemmanajemenmenu.model.Invoice
import com.riquest.sistemmanajemenmenu.model.InvoiceMenu
import com.riquest.sistemmanajemenmenu.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {
    private var TAG : String = "ASD"
    private var mTotalPesanan : Int = 0
    private lateinit var preferences: Preferences
    private lateinit var mList : ArrayList<Parcelable>
    private lateinit var mListMakanan : ArrayList<Food>

    //INITIALIZE FIREBASE VARIABLE
    lateinit var mFirebaseInstance : FirebaseDatabase
    lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        preferences = Preferences(baseContext)
        tvNama.setText(preferences.getValues("nama"))
        tvNomorMeja.setText(preferences.getValues("nomeja"))

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference("Invoice")

        //GETTING BUNDLE
        var bundle: Bundle ? = intent.extras
        if(bundle!!.getParcelableArrayList<Parcelable>("LIST")==null){
            Toast.makeText(this,"Data Kosong",Toast.LENGTH_LONG)
            Log.d(TAG, "onCreate: DATA KOSONG PAK")
            onBackPressed()
        }


        mList = bundle.getParcelableArrayList<Parcelable>("LIST") as ArrayList<Parcelable>
        mListMakanan = mList as ArrayList<Food>

        val mAdapter = CheckoutAdapter(mListMakanan,object : CheckoutAdapter.OnEventListener{
            override fun onKurang(harga: Int) {
                mTotalPesanan -= harga
                tvTotalPesanan.setText("Rp."+(mTotalPesanan).toString())
            }

            override fun onTambah(harga: Int) {
                mTotalPesanan += harga
                tvTotalPesanan.setText("Rp."+(mTotalPesanan).toString())
                var kjkjhkjhkjh =0
            }
        })

        rv_checkout.adapter = mAdapter
        rv_checkout.layoutManager = LinearLayoutManager(baseContext)

        btn_checkout.setOnClickListener{

            var menuList : ArrayList<InvoiceMenu> = ArrayList()
            for ((i,item) in mListMakanan.withIndex()){
//                val view: View? = (rv_checkout.layoutManager as LinearLayoutManager).findViewByPosition(i)
//                val tvQty : TextView = view?.findViewById(R.id.tv_qty)!!
                Log.d(TAG, "onCreate: THIS IS INDEX : "+i)
                val view : TextView? = rv_checkout.findViewHolderForAdapterPosition(i)?.itemView?.findViewById(R.id.tv_qty)
                val view1 : TextView? = rv_checkout.findViewHolderForAdapterPosition(i)?.itemView?.findViewById(R.id.tv_total_harga_item)
                menuList.add(InvoiceMenu(item.harga2,item.namamak,view?.text.toString(),view1?.text.toString()))
            }

            val invoice: Invoice = Invoice(menuList,preferences.getValues("nama"),preferences.getValues("nomeja"),"2021-06-22",mTotalPesanan)
            mDatabase.child(preferences.getValues("nama")+"_"+preferences.getValues("nomeja")).setValue(invoice)

            var intent = Intent(this@CheckoutActivity, ConfirmActivity::class.java)
            startActivity(intent)
        }
    hitungTotal()
    }

    fun hitungTotal(){
        var total=0
        for (item in mListMakanan){
            total += (item.qty!! * item.harga2!!) //Sebagai Satuan Dari Harga
        }
        mTotalPesanan = total;
        tvTotalPesanan.text = "Rp." + mTotalPesanan
    }

}