package com.rathalove.kunaphepshop.DataModel

data class ProductData(
    var product_id: String,
    var product_name: String,
    var product_price: String,
    var product_discount: Int,
    var category_id: String,
    var item: ItemData
)
