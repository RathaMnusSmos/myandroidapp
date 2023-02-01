package com.ratha.kunapheapmobile.RoomDataBase.UserLogIn

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_tbl")
data class UserData(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String
) {


}
