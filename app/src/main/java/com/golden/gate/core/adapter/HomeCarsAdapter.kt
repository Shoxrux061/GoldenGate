package com.golden.gate.core.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.golden.gate.core.room.RoomArticles
import com.golden.gate.databinding.ItemCarBinding

class HomeCarsAdapter(val context: Context) : RecyclerView.Adapter<HomeCarsAdapter.ViewHolder>() {

    private val data = ArrayList<RoomArticles>()

    fun setData(data: List<RoomArticles>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: RoomArticles) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                try {
                    val source =
                        ImageDecoder.createSource(context.contentResolver, Uri.parse(data.image))
                    val drawable = ImageDecoder.decodeDrawable(source)
                    binding.image.setImageDrawable(drawable)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("TAGError", "bindData: $e")
                }
            } else {
                binding.image.setImageURI(Uri.parse(data.image))
            }
            binding.currentPrice.text = data.currentPrice.plus("$")
            binding.name.text = data.name
            if (data.status) {
                binding.status.setTextColor(Color.GREEN)
                binding.status.text = "Rented"
            } else {

                binding.status.setTextColor(Color.RED)
                binding.status.text = "Not Rented"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }
}