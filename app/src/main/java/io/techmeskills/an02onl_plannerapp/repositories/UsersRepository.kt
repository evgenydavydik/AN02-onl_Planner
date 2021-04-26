package io.techmeskills.an02onl_plannerapp.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import io.techmeskills.an02onl_plannerapp.database.dao.UsersDao
import io.techmeskills.an02onl_plannerapp.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UsersRepository(
    context: Context,
    private val usersDao: UsersDao,
    private val appSettings: AppSettings
) {

    val userNames = usersDao.getAllUserNames()

    suspend fun login(userName: String) {
        withContext(Dispatchers.IO) {
            if (checkUserExists(userName).not()) {
                val userId = usersDao.newUser(User(name = userName))
                appSettings.setUserId(userId)
            } else {
                val userId = usersDao.getUserId(userName)
                appSettings.setUserId(userId)
            }
        }
    }

    private suspend fun checkUserExists(userName: String): Boolean {
        return withContext(Dispatchers.IO) {
            usersDao.getUsersCount(userName) > 0
        }
    }

    fun getCurrentUserFlow(): Flow<User> = appSettings.userIdFlow().flatMapLatest {
        usersDao.getById(it)
    }

    fun checkUserLoggedIn(): Flow<Boolean> =
        appSettings.userIdFlow().map { it >= 0 }.flowOn(Dispatchers.IO)

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            appSettings.setUserId(-1L)
        }
    }

    @SuppressLint("HardwareIds")
    val phoneId: String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}