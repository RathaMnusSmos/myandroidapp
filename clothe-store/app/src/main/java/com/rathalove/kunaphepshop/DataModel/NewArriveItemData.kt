package com.rathalove.kunaphepshop.DataModel.PojoAllProduct

import java.util.LinkedList

data class NewArriveItemData(
    var price: String,
    var name: String,
    var imgUrl: String,
    var colorItem: LinkedList<String>
)
