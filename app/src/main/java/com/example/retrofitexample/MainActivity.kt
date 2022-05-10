package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.example.retrofitexample.network.ApiClient
import com.example.retrofitexample.network.ApiService
import com.example.retrofitexample.network.Character
import com.example.retrofitexample.network.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.characterLiveData.observe(this) { state ->
            processCharactersResponse(state)
        }


    }


    private fun processCharactersResponse(state: ScreenState<List<Character>?>){
        when(state) {
            is ScreenState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE

            }
            is ScreenState.Success ->{
                binding.progressBar.visibility = View.GONE
                if(state.data != null){
                    val adapter = MainAdapter(state.data)
                    binding.charactersRV.adapter = adapter
                    binding.charactersRV.layoutManager = StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL)
                }
            }
            is ScreenState.Error ->{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this,state.message!!,Toast.LENGTH_LONG).show()
            }
        }

    }
}