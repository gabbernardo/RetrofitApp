package com.example.retrofitexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.retrofitexample.databinding.GridViewItemBinding
import com.example.retrofitexample.network.Character

class MainAdapter(val characterList: List<Character>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    // (1) creating inner class
    inner class MainViewHolder(private val itemBinding: GridViewItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        // (2) create a bind function
        fun bindData(character: Character) {
            itemBinding.image.load(character.image) {
                transformations(CircleCropTransformation())
            }
            itemBinding.name.text = character.name
            itemBinding.gender.text = character.gender
            itemBinding.status.text = character.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            GridViewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,false))
    }

    // (4) create a variable for binding the item
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val character = characterList[position]
        holder.bindData(character)
    }

    // (3) return to list.size
    override fun getItemCount(): Int {
        return characterList.size
    }


}