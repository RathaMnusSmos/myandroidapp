package com.ratha.kunapheapmobile.DataModel.PojoAllProduct

import com.google.gson.annotations.SerializedName

data class AllProduct (

  @SerializedName("product_id"       ) var productId       : String?         = null,
  @SerializedName("product_name"     ) var productName     : String?         = null,
  @SerializedName("product_price"    ) var productPrice    : String?         = null,
  @SerializedName("product_discount" ) var productDiscount : Any?            = null,
  @SerializedName("category_id"      ) var categoryId      : String?         = null,
  @SerializedName("item"             ) var item            : ArrayList<Item> = arrayListOf()

)