package com.casadocodigo.appcriptomoedas.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.casadocodigo.appcriptomoedas.R
import com.casadocodigo.commons.help.SharedPreference
import com.casadocodigo.commons.model.Coin
import com.squareup.picasso.Picasso

class CoinAdapter(private var list: List<Coin>, private var listener: MainActivity, private var context: Context ) :
        RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin, parent, false)

        return CoinViewHolder(view, list as MutableList<Coin>, listener, context)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CoinViewHolder(
        itemView: View,
        list: List<Coin>,
        listener: MainActivity,
        context: Context
    ) : RecyclerView.ViewHolder(itemView){
        private val listTitle: AppCompatTextView = itemView.findViewById(R.id.name_coin)
        private val idName: AppCompatTextView = itemView.findViewById(R.id.id_coin)
        private val listPrice: AppCompatTextView? = itemView?.findViewById(R.id.price_coin)
        private val imgCoin: AppCompatImageView = itemView.findViewById(R.id.imgcoin)
        private val starFavorite: AppCompatImageView = itemView.findViewById(R.id.star_favorites)
        private val shardPreference =  SharedPreference(context)

        init {
            itemView.setOnClickListener {
                listener.clickCoin(list[adapterPosition])
            }
        }
        fun bind(coin: Coin){
            val imageId = coin.idIcon?.replace("-", "")
            Picasso.get()
                .load("https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/${imageId}.png")
                .placeholder(R.mipmap.ic_icon)
                .into(imgCoin)
            if (coin.name?.isNotEmpty() == true){
                listTitle.text = coin.name.toString()
                idName.text = coin.assetId.toString()
                if (coin.priceUsd.toString() != "null"){
                    listPrice?.text = coin.priceUsd.toString()
                } else {
                    listPrice?.text = "0.0"
                }
                if (coin.favorites){
                    Picasso.get().load(R.drawable.ic_baseline_star_24).into(starFavorite)
                }
            }
            if (coin.assetId?.let { shardPreference.getBoolean(it) } == true){
                starFavorite.visibility = View.VISIBLE
            } else if (!coin.assetId?.let { shardPreference.getBoolean(it) } !! ){
                starFavorite.visibility = View.GONE
            }
        }


    }
}


