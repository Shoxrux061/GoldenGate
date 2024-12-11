package com.golden.gate.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.golden.gate.core.data.models.OnBoardModel
import com.golden.gate.databinding.ItemOnBoardBinding

class OnBoardAdapter : RecyclerView.Adapter<OnBoardAdapter.ViewHolder>() {

    private val data = ArrayList<OnBoardModel>()

    fun setData(data: List<OnBoardModel>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemOnBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: OnBoardModel) {
            binding.title1.text = data.title1
            binding.title2.text = data.title2
            binding.image.setImageResource(data.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOnBoardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }
}