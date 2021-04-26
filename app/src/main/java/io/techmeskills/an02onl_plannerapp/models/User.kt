package io.techmeskills.an02onl_plannerapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
open class User(
        @PrimaryKey(autoGenerate = true) val id: Long = 0L,
        val name: String
) : Parcelable