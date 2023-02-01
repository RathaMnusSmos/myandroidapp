package com.rathalove.kunaphepshop.DataPojo_AllProduct

import com.google.gson.annotations.SerializedName


data class Color (

  @SerializedName("color_id"   ) var colorId   : String? = null,
  @SerializedName("color_name" ) var colorName : String? = null

)