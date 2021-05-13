package io.techmeskills.an02onl_plannerapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["name"],
        childColumns = ["userName"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val date: String,
    val userName: String,
    val fromCloud: Boolean = false,
    val alarmEnabled: Boolean = false
) : Parcelable