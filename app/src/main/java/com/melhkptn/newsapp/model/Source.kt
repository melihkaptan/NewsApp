package com.melhkptn.newsapp.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Source(

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: String?,

    @SerializedName("name")
    val name: String?
)

