package com.riquest.sistemmanajemenmenu.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.riquest.sistemmanajemenmenu.CheckoutActivity
import com.riquest.sistemmanajemenmenu.R
import com.riquest.sistemmanajemenmenu.home.dashboard.DashboardFragment
import com.riquest.sistemmanajemenmenu.home.dashboard.DrinkFragment
import com.riquest.sistemmanajemenmenu.home.dashboard.FoodFragment
import kotlinx.android.synthetic.main.dashboard_menu.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.row_item_newmenu.*

class DashboardMenu : AppCompatActivity() {

    //INIT LIST PESANAN
    public lateinit var mList : ArrayList<Parcelable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.dashboard_menu)

        mList = ArrayList()
        val fragmentDashboard = DashboardFragment()
        val fragmentFood = FoodFragment()
        val fragmentDrink = DrinkFragment()
        setFragment(fragmentDashboard)

        iv_menu1.setOnClickListener{
            setFragment(fragmentDashboard)
            changeIcon(iv_menu1, R.drawable.dashboard_selected)
            changeIcon(iv_menu2, R.drawable.food)
            changeIcon(iv_menu3, R.drawable.minuman)
        }

        iv_menu2.setOnClickListener{
            setFragment(fragmentFood)
            changeIcon(iv_menu1, R.drawable.dashboard)
            changeIcon(iv_menu2, R.drawable.food_selected)
            changeIcon(iv_menu3, R.drawable.minuman)
        }

        iv_menu3.setOnClickListener{
            setFragment(fragmentDrink)
            changeIcon(iv_menu1, R.drawable.dashboard)
            changeIcon(iv_menu2, R.drawable.food)
            changeIcon(iv_menu3, R.drawable.minuman_selected)
        }

        imageView24.setOnClickListener{
            var intent : Intent = Intent(this@DashboardMenu,CheckoutActivity::class.java)
            intent.putExtra("LIST",mList)
            startActivity(intent)
        }
        tv_total_pesanan.setText("0")
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    public fun setTotalPesanan(tambahan:Int){
        val sekarang = tv_total_pesanan.text.toString().toInt()
        tv_total_pesanan.text = ""+(sekarang+tambahan)
//        this.tv_total_pesanan.setText((sekarang+tambahan))
    }
}