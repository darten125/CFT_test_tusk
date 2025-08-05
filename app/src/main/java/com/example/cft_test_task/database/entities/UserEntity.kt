package com.example.cft_test_task.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cft_test_task.model.Coordinates
import com.example.cft_test_task.model.DateInfo
import com.example.cft_test_task.model.Id
import com.example.cft_test_task.model.Login
import com.example.cft_test_task.model.Name
import com.example.cft_test_task.model.Picture
import com.example.cft_test_task.model.Street
import com.example.cft_test_task.model.Timezone


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    val gender: String,

    @Embedded(prefix = "name_")
    val name: Name,

    @Embedded(prefix = "street_")
    val street: Street,

    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "postcode") val postcode: String,

    @Embedded(prefix = "coordinates_")
    val coordinates: Coordinates,

    @Embedded(prefix = "timezone_")
    val timezone: Timezone,

    val email: String,

    @Embedded(prefix = "login_")
    val login: Login,

    @Embedded(prefix = "dob_")
    val dob: DateInfo,

    @Embedded(prefix = "registered_")
    val registered: DateInfo,

    val phone: String,
    val cell: String,

    @Embedded(prefix = "idinfo_")
    val idInfo: Id,

    @Embedded(prefix = "picture_")
    val picture: Picture,

    val nat: String
)

