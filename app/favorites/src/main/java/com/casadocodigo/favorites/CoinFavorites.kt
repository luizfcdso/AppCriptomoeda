package com.casadocodigo.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.casadocodigo.commons.help.SharedPreference
import com.casadocodigo.commons.model.Coin
import com.casadocodigo.commons.viewModel.CoinViewModel
import com.casadocodigo.details.DetailsCoin
import kotlinx.android.synthetic.main.activity_coin_favorite.*

class CoinFavorites : AppCompatActivity(){
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_favorite)
        sharedPreference = SharedPreference(this)
        val viewModel: CoinViewModel by viewModels()
        viewModel.init()
        viewModel.listCoin.observe(this, Observer {
            setAdapter(createListFavorite(it))
            button_main_fav.setOnClickListener { onClick(it) }
        })

    }

    private fun setAdapter(coin: List<Coin>) {
        list_recycler_favorite.layoutManager = GridLayoutManager(this@CoinFavorites, 2)
        list_recycler_favorite.adapter = coin?.let {
            FavoriteAdapter(
                coin,
                this
            )
        }

    }

    fun clickCoin(coin: Coin) {
        val intent = Intent(this, DetailsCoin::class.java)
        intent.putExtra("coin", coin)
        startActivity(intent)
        finish()
    }
    private fun createListFavorite(list: List<Coin>): MutableList<Coin>{
        var listCoinFavorites: MutableList<Coin> = arrayListOf()
        for (element in list){
            if (sharedPreference.getBoolean(element.assetId.toString())){
                listCoinFavorites.add(element)
            }
        }
        return listCoinFavorites
    }
    fun onClick(view: View){
        val id = view.id
        if (id == R.id.button_main_fav){
            val intent = Intent()
            intent.setClassName(this, "view.MainActivity")
            startActivity(intent)
            finish()
        }
    }
}