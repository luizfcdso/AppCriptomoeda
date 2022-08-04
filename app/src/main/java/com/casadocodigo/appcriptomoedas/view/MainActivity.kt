package com.casadocodigo.appcriptomoedas.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.casadocodigo.appcriptomoedas.R
import com.casadocodigo.commons.help.Date
import com.casadocodigo.commons.model.Coin
import com.casadocodigo.commons.viewModel.CoinViewModel
import com.casadocodigo.details.DetailsCoin
import com.casadocodigo.favorites.CoinFavorites
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val coinData = Date()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel: CoinViewModel by viewModels()
        viewModel.init()
        viewModel.listCoin.observe(this, Observer {
            setAdapter(it)


        })

        //mostrando a data atual no topo da tela
        val date: TextView = findViewById(R.id.data_top_bar)
        date.text = coinData.callDate()
        date.contentDescription = coinData.callDate()


        //fazendo com que os botões de moedas e favoritos ganham a função de click.
        button_coin.setOnClickListener {
            onClick(it)
        }
        button_star.setOnClickListener {
            onClick(it)
        }
    }

    private fun setAdapter(coin: List<Coin>?) {
        list_recycler_coin.layoutManager = GridLayoutManager(this@MainActivity, 1) //pegano a activity
        list_recycler_coin.adapter = coin?.let {
            CoinAdapter(
                coin,
                this,
                this
            )
        }
    }
    fun clickCoin(coin: Coin){
        val intent = Intent(this, DetailsCoin::class.java)
        intent.putExtra("coin", coin)
        startActivity(intent)
    }


    fun onClick(view: View) {
        val id = view.id

        when {
            (id == R.id.button_coin) -> {
                val intent = Intent(this, MainActivity::class.java) /*caso click no botão do valor da moeda, retorna a lista de criptomoedas que está na main*/
                startActivity(intent)
                finish()
            }
            (id == R.id.button_star) -> {
                val intentFavorites = Intent(this, CoinFavorites::class.java)/*caso click no botão de favoritos, retorna a tela de favoritos*/
                startActivity(intentFavorites)
            }
        }
    }
}