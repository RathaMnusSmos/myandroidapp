package com.ratha.kunapheapmobile.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.ratha.kunapheapmobile.R
import java.util.LinkedList

class ColorProductDetailAdapter(var context: Context, var listItem: LinkedList<String>): Adapter<ColorProductDetailAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.product_color_detail, parent,false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var colorCode = listItem[position]
        holder.color_item.setBackgroundColor(Color.parseColor(colorCode))
    }
    override fun getItemCount(): Int {
       return listItem.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var color_item : TextView = itemView.findViewById(R.id.color_detail)
    }

}