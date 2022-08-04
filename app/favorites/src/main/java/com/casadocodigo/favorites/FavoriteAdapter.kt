package com.casadocodigo.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.casadocodigo.commons.model.Coin
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val list: List<Coin>, private val listener: CoinFavorites) :
    RecyclerView.Adapter<FavoriteAdapter.CoinViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder{
        val View = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_favorite, parent, false)

        return CoinViewHolder(
            View,
            list as MutableList<Coin>,
            listener
        )
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CoinViewHolder(
        itemView: View,
        var list: MutableList<Coin>,
        var listener: CoinFavorites
    ) : RecyclerView.ViewHolder(itemView){
        private val listTitle: AppCompatTextView = itemView.findViewById(R.id.coin_name_fav)
        private val idName: AppCompatTextView = itemView.findViewById(R.id.coin_sub_name_fav)
        private val listPrice: AppCompatTextView = itemView.findViewById(R.id.coin_value_fav)
        private val imgCoin: AppCompatImageView = itemView.findViewById(R.id.coin_icon_fav)

        init {
            itemView.setOnClickListener{
                listener.clickCoin(list[adapterPosition])
            }
        }
        fun bind(coin: Coin){

            val imageId = coin.idIcon?.replace("-", "")
            Picasso.get()
                .load("https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/${imageId}.png")
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgCoin)
            if (coin.name?.isNotEmpty() == true){
                listTitle.text = coin.name.toString()
                idName.text = coin.assetId.toString()
                listPrice.text = coin.priceUsd.toString()

                if (coin.priceUsd.toString() != "null"){
                    listPrice.text = coin.priceUsd.toString()
                } else {
                    listPrice.text = "00.00"
                }
            }


        }
    }
}