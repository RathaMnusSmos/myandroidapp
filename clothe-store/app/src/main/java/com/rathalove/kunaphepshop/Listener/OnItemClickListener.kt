package com.rathalove.kunaphepshop.Listener

import com.rathalove.kunaphepshop.DataModel.PojoAllProduct.AllProduct
import com.rathalove.kunaphepshop.DataPojo_AllCategory.AllCategoryData


interface OnItemClickListener {

    fun onTopCategoryItemClick(pos: Int, data: AllCategoryData)
    fun onNewArriveItemClick(pos: Int, data: AllProduct)
}