package io.techmeskills.an02onl_plannerapp

import android.app.Application
import io.techmeskills.an02onl_plannerapp.cloud.ApiCloud
import io.techmeskills.an02onl_plannerapp.database.DatabaseConstructor
import io.techmeskills.an02onl_plannerapp.database.PlannerDatabase
import io.techmeskills.an02onl_plannerapp.datastore.AppSettings
import io.techmeskills.an02onl_plannerapp.repositories.CloudRepository
import io.techmeskills.an02onl_plannerapp.repositories.NotesRepository
import io.techmeskills.an02onl_plannerapp.repositories.UsersRepository
import io.techmeskills.an02onl_plannerapp.screen.login.LoginFragment
import io.techmeskills.an02onl_plannerapp.screen.login.LoginViewModel
import io.techmeskills.an02onl_plannerapp.screen.main.MainViewModel
import io.techmeskills.an02onl_plannerapp.screen.note_details.NoteDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PlannerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlannerApp)
            modules(listOf(viewModels, storageModule, repositoryModule, cloudModule))
        }
    }

    private val viewModels = module {
        viewModel { MainViewModel(get(), get(), get()) }
        viewModel { NoteDetailsViewModel(get()) }
        viewModel { LoginViewModel(get()) }
    }

    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }  //создаем синглтон базы данных
        factory { get<PlannerDatabase>().notesDao() } //предоставляем доступ для конкретной Dao (в нашем случае NotesDao)
        factory { get<PlannerDatabase>().usersDao() }
        single { AppSettings(get()) }
    }
    private val repositoryModule = module {
        factory { UsersRepository(get(), get(), get()) }
        factory { NotesRepository(get(), get()) }
        factory { CloudRepository(get(), get(), get()) }
    }

    private val cloudModule = module {
        factory { ApiCloud.get() }
    }
}
