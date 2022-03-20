package com.developer.takenote.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.developer.takenote.featurecreateuser.data.repository.UserRepositoryImpl
import com.developer.takenote.featurecreateuser.domain.repository.UserRepository
import com.developer.takenote.featurecreateuser.domain.usecase.*
import com.developer.takenote.featurelogin.data.repository.LoginRepositoryImpl
import com.developer.takenote.featurelogin.domain.repository.LoginRepository
import com.developer.takenote.featurelogin.domain.usecase.DeleteLoginUseCase
import com.developer.takenote.featurelogin.domain.usecase.InsertLoginUseCase
import com.developer.takenote.featurelogin.domain.usecase.LogOutUserUseCase
import com.developer.takenote.featurelogin.domain.usecase.UserLoginUseCases
import com.developer.takenote.featurenote.data.datasource.DataBaseMigrationHelper
import com.developer.takenote.featurenote.data.datasource.NoteDatabase
import com.developer.takenote.featurenote.data.repository.NoteRepositoryImpl
import com.developer.takenote.featurenote.domain.repository.NoteRepository
import com.developer.takenote.featurenote.domain.usecase.*
import com.developer.takenote.ui.SharedPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDataBaseHelper(): DataBaseMigrationHelper {
        return DataBaseMigrationHelper()
    }

    @Provides
    @Singleton
    fun provideNoteDatabase(
        app: Application,
        dataBaseMigrationHelper: DataBaseMigrationHelper
    ): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java, NoteDatabase.DATABASE_NAME
        ).addMigrations(
            dataBaseMigrationHelper.MIGRATION_1_2,
            dataBaseMigrationHelper.MIGRATION_1_4,
            dataBaseMigrationHelper.MIGRATIN_2_3,
            dataBaseMigrationHelper.MIGRATIN_3_4
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }


    @Provides
    @Singleton
    fun provideUserRepository(db: NoteDatabase): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(db: NoteDatabase): LoginRepository {
        return LoginRepositoryImpl(db.loginDao)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceRepository(dataStore: DataStore<Preferences>): SharedPreferenceRepository {
        return SharedPreferenceRepository(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            insertUserUseCase = InsertUserUseCase(userRepository = repository),
            getAllUsersUseCase = GetAllUsersUseCase(userRepository = repository),
            getLastInsertedUserUseCase = GetLastInsertedUserUseCase(userRepository = repository),
            getUserByIdUseCase = GetUserByIdUseCase(userRepository = repository),
            permanentDeleteUserAccountUseCase = PermanentDeleteUserAccountUseCase(
                userRepository = repository
            )

        )
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            delete = DeleteNoteUseCase(repository = repository),
            insertNote = InsertNoteUseCase(repository = repository),
            getNoteById = GetNoteByIdUseCase(repository = repository),
            getNotesFromMail = GetNotesFromMailIDUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(
        loginRepository: LoginRepository,
        userRepository: UserRepository,
        userSharedPreferenceRepository: SharedPreferenceRepository
    ): UserLoginUseCases {
        return UserLoginUseCases(
            insertLoginUseCase = InsertLoginUseCase(
                loginRepository,
                userRepository,
                userSharedPreferenceRepository
            ),
            deleteLoginUseCase = DeleteLoginUseCase(loginRepository),
            logOutUserUseCase = LogOutUserUseCase(loginRepository)
        )
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(context = context, "shared_pres")),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                context.preferencesDataStoreFile("shared_pres")
            }
        )
    }


}