package com.luc.data.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.luc.common.entities.CalderaEntity
import com.luc.common.entities.RepuestoEntity
import com.luc.common.entities.SettingsEntity
import com.luc.common.model.Caldera
import com.luc.common.model.Repuesto
import com.luc.common.model.asCalderaEntity
import com.luc.data.DomainRepositoryImpl
import com.luc.data.di.DataModule.Companion.provideDatabase
import com.luc.data.local.LocalDataSource
import com.luc.data.local.LocalDatabase
import com.luc.data.local.dao.RepuestoDao
import com.luc.data.local.dao.CalderaDao
import com.luc.data.local.dao.SettingsDao
import com.luc.data.remote.firebase.firestore.FirestoreData
import com.luc.data.remote.firebase.firestore.FirestoreDataImpl
import com.luc.domain.DomainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidApplication
import org.koin.core.component.getScopeId
import org.koin.dsl.module
import java.io.FileReader

val firebaseModule = module {
    single { FirebaseFirestore.getInstance() }
}

class DataModule {
    companion object {
        private var INSTANCE: LocalDatabase? = null

        val applicationScope = CoroutineScope(SupervisorJob())

        @Synchronized
        fun getInstance(context: Application): LocalDatabase =
            INSTANCE ?: provideDatabase(context).also { INSTANCE = it }

        fun provideDatabase(application: Application): LocalDatabase {
            return Room.databaseBuilder(application, LocalDatabase::class.java, "db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        applicationScope.launch(Dispatchers.IO) {
                            getInstance(application).settingsDao().insert(SettingsEntity())
                            val gson = Gson()
                            val jsonString =
                                application.assets.open("ariston.json").bufferedReader()
                                    .use { it.readText() }
                            val jsonData: Array<Caldera> =
                                gson.fromJson(jsonString, Array<Caldera>::class.java)

                            jsonData.forEach {
                                val caldera =
                                    Caldera(caldera = it.caldera, repuestos = it.repuestos)

                                getInstance(application).calderaDao()
                                    .insert(caldera.asCalderaEntity())
                                it.repuestos.forEach { rep ->

                                    val repuesto = Repuesto(
                                        descripcion = rep.descripcion,
                                        codigo = rep.codigo,
                                        precioPublico = rep.precioPublico,
                                        precioService = rep.precioService
                                    )
                                    getInstance(application).repuestoDao().insert(
                                        RepuestoEntity(
                                            repuesto.id,
                                            caldera.id,
                                            repuesto.descripcion,
                                            repuesto.codigo,
                                            repuesto.precioService,
                                            repuesto.precioPublico
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
                .build()
        }
    }
}

val roomModule = module {

    fun provideCalderaDao(database: LocalDatabase): CalderaDao {
        return database.calderaDao()
    }

    fun provideRepuestoDao(database: LocalDatabase): RepuestoDao {
        return database.repuestoDao()
    }

    fun provideSettingsDao(database: LocalDatabase): SettingsDao {
        return database.settingsDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCalderaDao(get()) }
    single { provideRepuestoDao(get()) }
    single { provideSettingsDao(get()) }
}

val repositoryModule = module {
    factory<FirestoreData> {
        FirestoreDataImpl(
            firestore = get(),
        )
    }
    factory { LocalDataSource(get(), get(), get()) }
    factory<DomainRepository> { DomainRepositoryImpl(get()) }
}

val dataModule = listOf(repositoryModule, firebaseModule, roomModule)