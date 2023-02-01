package com.ratha.kunapheapmobile.DataModel

import java.util.LinkedList

data class NewArriveItemData(
    var price: String,
    var name: String,
    var imgUrl: String,
    var colorItem: LinkedList<String>
)
