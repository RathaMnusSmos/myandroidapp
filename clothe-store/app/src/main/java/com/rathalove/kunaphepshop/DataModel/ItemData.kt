package com.rathalove.kunaphepshop.DataModel

data class ItemData(
    var item_id: String,
    var product_id: String,
    var item_amount: Int,
    var item_created_date: String,
    var item_last_modify_date: String,
    var color_id: String,
    var size_id: String,
    var image:DataImageSample
)
