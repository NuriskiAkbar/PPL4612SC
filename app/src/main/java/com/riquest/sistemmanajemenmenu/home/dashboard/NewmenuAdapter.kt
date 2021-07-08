package com.riquest.sistemmanajemenmenu.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riquest.sistemmanajemenmenu.R
import com.riquest.sistemmanajemenmenu.model.Food
import kotlinx.android.synthetic.main.row_item_newmenu.view.*

class NewmenuAdapter(private var data: List<Food>,
                     private val listener:(Food) -> Unit) : RecyclerView.Adapter<NewmenuAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewmenuAdapter.ViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_newmenu, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: NewmenuAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val tvNamamakanan:TextView = view.findViewById(R.id.tv_nama_makanan2)
        private val tvHargamakanan:TextView = view.findViewById(R.id.tv_harga)
        private val btnPesan : Button = view.findViewById(R.id.btn_pesan2)
        private val ivPostermakanan:ImageView = view.findViewById(R.id.iv_poster_makanan)

        fun bindItem(data:Food, listener: (Food) -> Unit, context: Context){
            tvNamamakanan.setText(data.namamak)
            tvHargamakanan.setText(data.harga)
            btnPesan.setText("Pesan")

            Glide.with(context)
                    .load(data.poster)
                    .into(ivPostermakanan)

            btnPesan.setOnClickListener{
                listener(data)
            }
        }
    }
}
