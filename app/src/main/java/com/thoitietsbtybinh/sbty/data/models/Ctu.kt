package com.thoitietsbtybinh.sbty.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity
data class Ctu(
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "isSaved")
    var isSaved: Int? = null
)