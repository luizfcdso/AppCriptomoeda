package com.casadocodigo.commons.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.casadocodigo.commons.api.CoinService
import com.casadocodigo.commons.model.Coin
import retrofit2.Call
import retrofit2.Response

open class CoinViewModel(applicationContext: Any) : ViewModel() {
    val listCoinResult: MutableList<Coin> = arrayListOf()
    private val coinLiveData: MutableLiveData<List<Coin>> = MutableLiveData()
    val listCoin: MutableLiveData<List<Coin>>
        get() = coinLiveData

    fun addNewTask(){
        newTaskEvent()
    }

    fun newTaskEvent() {
        val call = CoinService.coinRetrofitApi()
            .getAllList("07D1A571-6EBA-41BC-AB3A-B74F9164D071")
        call.enqueue(object : retrofit2.Callback<List<Coin>> {


            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                if (response.isSuccessful)
                    response.body()?.forEach {
                        listCoinResult.add(it)
                    } else {
                    response.errorBody()?.let {
                        when (response.code()) {
                            400 -> ("Bad resquest")
                            401 -> ("Unauthorized")
                            403 -> ("Forbidden")
                            429 -> ("too many request")
                            550 -> ("No date")
                            else -> ("Error")
                        }
                    }
                }
                coinLiveData.postValue(listCoinResult)
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                    coinLiveData.postValue(null)
            }
        })
    }

}