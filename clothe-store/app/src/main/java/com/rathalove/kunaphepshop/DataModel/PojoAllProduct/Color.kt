package com.rathalove.kunaphepshop.DataModel.PojoAllProduct

import com.google.gson.annotations.SerializedName


data class Color (

  @SerializedName("color_id"   ) var colorId   : String? = null,
  @SerializedName("color_name" ) var colorName : String? = null

)