package com.ratha.kunapheapmobile.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ratha.kunapheapmobile.DataModel.CategoryData
import com.ratha.kunapheapmobile.DataModel.TopCategoryItemData
import com.ratha.kunapheapmobile.DataPojo_AllCategory.AllCategoryData
import com.ratha.kunapheapmobile.Listener.OnItemClickListener
import com.ratha.kunapheapmobile.R
import com.squareup.picasso.Picasso
import java.util.LinkedList

class TopCategoryAdapter(
    var context: Context,
    var listItem: LinkedList<AllCategoryData>,
    var listener: OnItemClickListener
) : Adapter<TopCategoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.top_category_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var imgUrl = listItem[position].product[0].item[0].image[0].imageLink
        Picasso.get().load(imgUrl).into(holder.imgProduct)
        holder.nameProduct.text = listItem[position].categoryName

        holder.imgProduct.setOnClickListener {
            listener.onTopCategoryItemClick(position, listItem[position])
            Toast.makeText(context, "clicked $position!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }


    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        var imgProduct: ImageView = itemView.findViewById(R.id.top_category_product)
        var nameProduct: TextView = itemView.findViewById(R.id.top_category_itemName)
    }


}