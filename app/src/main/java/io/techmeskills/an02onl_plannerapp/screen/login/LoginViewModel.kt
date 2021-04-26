package io.techmeskills.an02onl_plannerapp.screen.login

import androidx.lifecycle.*
import io.techmeskills.an02onl_plannerapp.database.dao.UsersDao
import io.techmeskills.an02onl_plannerapp.models.User
import io.techmeskills.an02onl_plannerapp.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.support.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginViewModel(private val usersRepository: UsersRepository) : CoroutineViewModel() {

    val autoCompleteUsersLiveData = usersRepository.userNames.asLiveData()

    val logged: LiveData<Boolean> = usersRepository.checkUserLoggedIn().asLiveData()

    val errorLiveData = MutableLiveData<String>()

    fun login(user: String){
        launch {
            try {
                if (user.isNotBlank()) {
                    usersRepository.login(user)
                } else {
                    errorLiveData.postValue("Please, enter your name")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e.message)
            }
        }
    }
}