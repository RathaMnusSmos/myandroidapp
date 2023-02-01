package com.rathalove.kunaphepshop.DataPojo_AllCategory

import com.google.gson.annotations.SerializedName


data class AllCategoryData (

  @SerializedName("category_id"   ) var categoryId   : String?            = null,
  @SerializedName("category_name" ) var categoryName : String?            = null,
  @SerializedName("product"       ) var product      : ArrayList<Product> = arrayListOf()

)