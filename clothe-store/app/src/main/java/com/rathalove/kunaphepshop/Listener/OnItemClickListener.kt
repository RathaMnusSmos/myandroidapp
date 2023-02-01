package com.ratha.kunapheapmobile.Listener

import com.ratha.kunapheapmobile.DataModel.CategoryData
import com.ratha.kunapheapmobile.DataModel.NewArriveItemData
import com.ratha.kunapheapmobile.DataModel.PojoAllProduct.AllProduct
import com.ratha.kunapheapmobile.DataModel.TopCategoryItemData
import com.ratha.kunapheapmobile.DataPojo_AllCategory.AllCategoryData

interface OnItemClickListener {

    fun onTopCategoryItemClick(pos: Int, data:AllCategoryData)
    fun onNewArriveItemClick(pos: Int, data: AllProduct)
}