package yan.zubritskiy.templatemvvm

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import yan.zubritskiy.templatemvvm.di.commonModule
import yan.zubritskiy.templatemvvm.di.localModule
import yan.zubritskiy.templatemvvm.di.networkModule
import yan.zubritskiy.templatemvvm.di.repositoryModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(networkModule, localModule, repositoryModule, commonModule)
        }
    }
}