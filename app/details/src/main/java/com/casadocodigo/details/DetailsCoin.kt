package com.casadocodigo.details

// criado para mostrar a tela de detalhes de cada moeda.


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.casadocodigo.commons.help.SharedPreference
import com.casadocodigo.commons.model.Coin
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_coin.*

class DetailsCoin : AppCompatActivity() {

    private var coin: Coin? = null
    private lateinit var shardPreference: SharedPreference
    private var iconId: String? = ""
    private var priceId: String? = ""
    private var hourId: String? = ""
    private var dayId: String? = ""
    private var monthId: String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_coin)

        shardPreference = SharedPreference(this)

        getExtra()
        button_back.setOnClickListener{
            clickButtonBack(it)
        }
        button_favorite.setOnClickListener{ onClickStatusCoin(it)}
        checkButton()
    }

    private fun getExtra(){
        if (intent.extras != null){
            val coin = intent.getParcelableExtra("coin") as? Coin
            iconId = coin?.assetId
            priceId = coin?.priceUsd
            hourId = coin?.volumeHour
            dayId = coin?.volumeDay
            monthId = coin?.volumeMonth

            name_coin.text = coin?.assetId
            price.text = coin?.priceUsd ?: "00.00"
            volume_last_hour.text = coin?.volumeHour
            volume_last_day.text = coin?.volumeDay
            volume_last_month.text = coin?.volumeMonth

            val image = coin?.idIcon?.replace("-", "")
            Picasso.get()
                .load("https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_32/${image}.png")
                .placeholder(R.mipmap.ic_details)
                .into(id_icon_details)

            if (coin != null){
                if (coin.assetId?.let { shardPreference.getBoolean(it)  } == true){
                    star_favorite.visibility = View.VISIBLE
                } else if (!coin.assetId?.let { shardPreference.getBoolean(it) }!!){
                    star_favorite.visibility = View.GONE
                }
            }
        }
    }

    private fun clickButtonBack(view: View) {
        val page = button_back.id
        if (page == view.id){
            val intent = Intent()
            intent.setClassName(this, "com.casadocodigo.view.MainActivity")
            startActivity(intent)
            finish()

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun onClickStatusCoin(view: View){
        if (button_favorite.text == "ADICIONAR"){
            shardPreference.storeBoolean(iconId.toString(),true)
            checkButton()
            coin?.favorites = false
        } else if (button_favorite.text == "REMOVER"){
            shardPreference.storeBoolean(iconId.toString(), false)
            checkButton()
            coin?.favorites = true
        }
    }
    private fun checkButton(){
        if (shardPreference.getBoolean(iconId.toString())){
            button_favorite.text = "REMOVER"
            star_favorite.visibility = View.VISIBLE
        } else if (!shardPreference.getBoolean(iconId.toString())){
            button_favorite.text = "ADICIONAR"
            star_favorite.visibility = View.GONE
        }
    }


}