package io.techmeskills.an02onl_plannerapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.techmeskills.an02onl_plannerapp.models.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UsersDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun saveUser(user: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateUser(user: User)

    @Delete
    abstract fun deleteUser(user: User)

    @Query("SELECT COUNT(*) FROM users WHERE name == :userName")
    abstract fun getUsersCount(userName: String): Int

    @Query("SELECT id FROM users WHERE name == :userName")
    abstract fun getUserId(userName: String): Long

    @Query("SELECT * FROM users WHERE id == :userId")
    abstract fun getById(userId: Long): Flow<User>

    @Query("SELECT * FROM users ORDER BY id DESC")
    abstract fun getAllUsers(): List<User>

    @Query("SELECT * FROM users where id==:userId ORDER BY id DESC")
    abstract fun getUser(userId:Long): User

    @Query("SELECT name FROM users")
    abstract fun getAllUserNames(): Flow<List<String>>

    @Query("SELECT * FROM users ORDER BY id DESC")
    abstract fun getAllUsersLiveFlow(): Flow<List<User>>

    @Query("SELECT * FROM users ORDER BY id DESC")
    abstract fun getAllUsersLiveData(): LiveData<List<User>>
}