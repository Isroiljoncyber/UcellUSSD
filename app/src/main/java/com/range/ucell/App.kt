package com.range.ucell

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import com.range.ucell.data.db.MobiuzDatabase
import com.range.ucell.data.db.entity.SaleModel
import com.range.ucell.data.network.ApiService
import com.range.ucell.data.pravider.UnitProvider
import com.range.ucell.data.pravider.UnitProviderImpl
import com.range.ucell.data.repository.MobiuzRepository
import com.range.ucell.data.repository.MobiuzRepositoryImpl
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: MultiDexApplication(), KodeinAware {

    // We use inject
    @Inject
    lateinit var apiService: ApiService

    override val kodein: Kodein
        get() = Kodein.lazy {

            import(androidXModule(this@App))

            bind() from singleton { MobiuzDatabase(instance()) }
            bind() from singleton { instance<MobiuzDatabase>().mobiuzDao() }
            bind() from singleton { apiService }

            bind<MobiuzRepository>() with singleton { MobiuzRepositoryImpl(instance(), instance(), instance()) }

            bind<UnitProvider>() with singleton { UnitProviderImpl(instance(), instance()) }

        }


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

    companion object {
        var isLoaded: Boolean = true
        var sale: SaleModel? = null
    }
}