package com.rathalove.kunaphepshop.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rathalove.kunaphepshop.DataModel.PojoAllProduct.AllProduct
import com.rathalove.kunaphepshop.Listener.OnItemClickListener

import com.rathalove.kunaphepshop.R

import com.squareup.picasso.Picasso
import java.util.*

class NewArriveAdapter(
    var context: Context,
    var listItem: LinkedList<AllProduct>,
    var listener: OnItemClickListener
) : Adapter<NewArriveAdapter.MyViewHolder>() {

    private var colors: LinkedList<String> = LinkedList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.new_arrive_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var imgUrl = listItem[position].item[0].image[0].imageLink
        Picasso.get().load(imgUrl).into(holder.imgProduct)
        holder.nameProduct.text = listItem[position].productName.toString()
        holder.productPrice.text = "$ ${listItem[position].productPrice}"
        colors.clear()
        for(i in listItem[position].item){
            var color = i.ColorOnSide!!.color!!.colorName.toString()
            if (!colors.contains(color)){
                colors.add(color)
            }
        }

        var colorItemAdapter: CardItemAdapter = CardItemAdapter(context, colors)
        val cardColorItemLayout: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        holder.color_rec.layoutManager = cardColorItemLayout
        holder.color_rec.adapter = colorItemAdapter


        holder.imgProduct.setOnClickListener {
            listener.onNewArriveItemClick(position, listItem[position])
            Toast.makeText(context, "clicked $position!", Toast.LENGTH_SHORT).show()

        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.new_arrive_image)
        var productPrice: TextView = itemView.findViewById(R.id.new_arrive_itemPrice)
        var nameProduct: TextView = itemView.findViewById(R.id.new_arrive_itemName)
        var color_rec : RecyclerView = itemView.findViewById(R.id.color_item_Recycle)
    }
}