package io.techmeskills.an02onl_plannerapp.cloud

import com.google.gson.annotations.SerializedName

class CloudNote(
    @SerializedName("title")
    val title: String,

    @SerializedName("date")
    val date: Long,

    @SerializedName("alarmEnabled")
    val alarmEnabled: Boolean,

    @SerializedName("noteColor")
    val noteColor: String,

    @SerializedName("notePinned")
    val notePinned: Boolean
)