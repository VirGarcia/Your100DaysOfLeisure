package com.example.your100daysofleisure.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.your100daysofleisure.R
import com.example.your100daysofleisure.data.Leisure
import com.example.your100daysofleisure.databinding.ItemLeisureBinding
import androidx.core.content.ContextCompat
import com.example.your100daysofleisure.utils.SessionManager

lateinit var session: SessionManager
class Your100DaysOfLeisureAdapter ( private var dataSet: List<Leisure> = emptyList(),
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
    fun render(leisure: Leisure) {
        //binding.nameTextView.text = leisure.address?.area?.postalCode
        binding.titleTextView.text = leisure.title
        binding.zipCodeTextView.text = leisure.address?.postalCode()

        session = SessionManager(itemView.context)
        if (leisure.address?.postalCode() == session.getUserZipCode()) {
            val backgroundColor = ContextCompat.getColor(binding.root.context, R.color.green)
            binding.details.setBackgroundColor(backgroundColor)
        } else {
            val backgroundColor = ContextCompat.getColor(binding.root.context, R.color.transparent_grey)
            binding.details.setBackgroundColor(backgroundColor)
        }
    }
}
