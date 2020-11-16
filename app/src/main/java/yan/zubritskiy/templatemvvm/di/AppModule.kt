package yan.zubritskiy.templatemvvm.di

import androidx.navigation.NavController
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import yan.zubritskiy.templatemvvm.business.local.Storage
import yan.zubritskiy.templatemvvm.business.network.AuthNetworkDataSource
import yan.zubritskiy.templatemvvm.business.repository.AuthRepository
import yan.zubritskiy.templatemvvm.framework.local.AppStorage
import yan.zubritskiy.templatemvvm.framework.repository.AuthRepositoryImpl
import yan.zubritskiy.templatemvvm.ui.navigation.Navigator
import yan.zubritskiy.templatemvvm.ui.navigation.NavigatorImpl

private val UNATHORIZED_QUALIFIER = named("scoped_unathorized")
val PARAM_NAVIGATOR = "PARAM_NAVIGATOR"

sealed class Scope {
    object Authorized: Scope()
    object Unauthorized: Scope()
}

val networkModule = module {
    factory { Scope.Unauthorized }
    scope<Scope.Unauthorized> {
        scoped<AuthNetworkDataSource>(qualifier = UNATHORIZED_QUALIFIER) {
            object : AuthNetworkDataSource {
                override suspend fun signIn(nickName: String, password: String) {
                    TODO("Not yet implemented")
                }

                override suspend fun signUp(nickName: String, password: String) {
                    TODO("Not yet implemented")
                }

            }
        }
    }
}

val commonModule = module {
    factory<Navigator> { (navController: NavController) -> NavigatorImpl(navController) }
}

val localModule = module {
    single<Storage> { AppStorage(androidContext()) }
}

val repositoryModule = module {
    scope<Scope.Unauthorized> {
        scoped<AuthRepository>(qualifier = UNATHORIZED_QUALIFIER) {
            AuthRepositoryImpl(get(qualifier = UNATHORIZED_QUALIFIER), get())
        }
    }
}