package com.testproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.testproject.R
import com.testproject.data.network.response.response_models.Item

class ItemsAdapter(private var listArray: List<Item>? = null) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvItemPrice: TextView = itemView.findViewById(R.id.tvItemPrice)
        val tvItemDetails: TextView = itemView.findViewById(R.id.tvItemDetails)
    }
    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }
    override
    fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val listItem = listArray?.get(position)
        listItem?.let {
            holder.tvItemPrice.text = "Price: "+listItem.price
            holder.tvItemDetails.text = listItem.description
            holder.tvItemName.text = listItem.name
        }
    }
    override
    fun getItemCount(): Int {
        return listArray?.size?:0
    }

    fun setData( listArray: List<Item>)
    {
        this.listArray = listArray
        notifyDataSetChanged()
    }
}