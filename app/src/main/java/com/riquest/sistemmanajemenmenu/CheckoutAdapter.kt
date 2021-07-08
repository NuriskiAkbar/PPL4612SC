package com.riquest.sistemmanajemenmenu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riquest.sistemmanajemenmenu.model.Food

class CheckoutAdapter(private var data: ArrayList<Food>, private val listener:OnEventListener) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>()  {

    lateinit var contextAdapter : Context
    interface OnEventListener{
        fun onTambah(nilai: Int)
        fun onKurang(nilai: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ASD", "onCreateViewHolder: SAMPAI SINI")
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data.get(position), listener, contextAdapter)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        public var currentQty = 1
        private val tvNama: TextView = view.findViewById(R.id.tv_judul)
        private val tvTotalHarga : TextView = view.findViewById(R.id.tv_total_harga_item)
        private val tvHarga: TextView = view.findViewById(R.id.tv_subharga)
        private val tvQTY : TextView = view.findViewById(R.id.tv_qty)
        private val ivPlus : ImageView = view.findViewById(R.id.iv_plus)
        private val ivMin : ImageView = view.findViewById(R.id.iv_min)
        private val ivPoster: ImageView = view.findViewById(R.id.iv_img)


        fun bindItem(data: Food?, listener: OnEventListener, context: Context){
            tvNama.setText(data?.namamak)
            tvHarga.setText(data?.harga)
            tvTotalHarga.setText((data?.harga2!! * data?.qty!!).toString())
            tvQTY.setText((data.qty!!).toString())

            ivPlus.setOnClickListener {
                currentQty+=1
                tvQTY.setText((currentQty).toString())
                tvTotalHarga.setText((currentQty * data.harga2!!).toString())
                listener.onTambah(data.harga2!!)
            }

            ivMin.setOnClickListener{
                if(currentQty>1){
                    currentQty--
                    tvQTY.setText((currentQty).toString())
                    tvTotalHarga.setText((currentQty* data.harga2!!).toString())
                    listener.onKurang(data.harga2!!)
                }
            }
//
            Glide.with(context)
                    .load(data?.poster)
                    .into(ivPoster)
//
//
//            itemView.setOnClickListener{
//                listener(data)
//            }
        }
    }
}
