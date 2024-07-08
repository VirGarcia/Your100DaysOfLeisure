package com.example.your100daysofleisure.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your100daysofleisure.data.Leisure
import com.example.your100daysofleisure.databinding.ItemLeisureBinding

class LeisureAdapter ( private var dataSet: List<Leisure> = emptyList(),
                         private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<LeisureViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeisureViewHolder {
        val binding = ItemLeisureBinding.inflate(LayoutInflater.from(parent.context))
        return LeisureViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size
    override fun onBindViewHolder(holder: LeisureViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(position)
        }
    }
    fun updateData(dataSet: List<Leisure>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}
class LeisureViewHolder(private val binding: ItemLeisureBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(superhero: Leisure) {
        binding.nameTextView.text = leisure.name
    }
}
