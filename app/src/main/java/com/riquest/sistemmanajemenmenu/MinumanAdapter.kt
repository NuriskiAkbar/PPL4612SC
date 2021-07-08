package com.riquest.sistemmanajemenmenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riquest.sistemmanajemenmenu.model.Food

class MinumanAdapter(private var data: List<Food>,
                     private val listener:(Food) -> Unit) : RecyclerView.Adapter<MinumanAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_minuman, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val tvNamaminuman:TextView = view.findViewById(R.id.tv_nama_makanan)
        private val tvTag1:TextView = view.findViewById(R.id.tag)
        private val tvTag2:TextView = view.findViewById(R.id.tag4)
        private val tvHargaminuman:TextView = view.findViewById(R.id.tv_harga)
        private val btnPesan : Button = view.findViewById(R.id.btn_pesan)
        private val ivPosterminuman:ImageView = view.findViewById(R.id.iv_poster_makanan)


        fun bindItem(data:Food, listener: (Food) -> Unit, context: Context){
            tvNamaminuman.setText(data.namamak)
            tvTag1.setText(data.tag1)
            tvTag2.setText(data.tag2)
            tvHargaminuman.setText(data.harga)

            Glide.with(context)
                    .load(data.poster)
                    .into(ivPosterminuman)


            btnPesan.setOnClickListener{
                listener(data)
            }
        }
    }
}
