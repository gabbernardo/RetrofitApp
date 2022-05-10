package com.example.retrofitexample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.network.ApiClient
import com.example.retrofitexample.network.Character
import com.example.retrofitexample.network.CharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository
                    = Repository(ApiClient.apiService)) : ViewModel() {

    private var _characterLiveData = MutableLiveData<ScreenState<List<Character>?>>()
    val characterLiveData: LiveData<ScreenState<List<Character>?>>
    get () =  _characterLiveData


    init {
        fetchCharacters()
    }

    private fun fetchCharacters(){

        _characterLiveData.postValue(ScreenState.Loading(null))

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("MainViewModel", Thread.currentThread().name)
            try{
                val client = repository.getCharacters("2")
                _characterLiveData.postValue(ScreenState.Success(client.result))
            }catch (e: Exception){
                Log.e("Error in API",e.message.toString())
                _characterLiveData.postValue(ScreenState.Error(e.message.toString(), null))
            }
        }


    }

}