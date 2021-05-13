package io.techmeskills.an02onl_plannerapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.techmeskills.an02onl_plannerapp.models.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UsersDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun saveUser(user: User)

    @Delete
    abstract fun deleteUser(user: User)

    @Query("SELECT COUNT(*) FROM users WHERE name == :userName")
    abstract fun getUsersCount(userName: String): Int

    @Query("SELECT name FROM users WHERE name == :userName")
    abstract fun getUserId(userName: String): String

    @Query("SELECT * FROM users WHERE name == :userName")
    abstract fun getById(userName: String): Flow<User>

    @Query("SELECT name FROM users")
    abstract fun getAllUserNames(): Flow<List<String>>
}