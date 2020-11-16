package yan.zubritskiy.templatemvvm.ui.navigation

import androidx.navigation.NavController
import yan.zubritskiy.templatemvvm.R

class NavigatorImpl(private val navController: NavController): Navigator {
    override fun goTo(destination: Destination) {
        when(destination) {
            is Destination.Authorization.Onboarding -> navController.navigate(R.id.onboardingFragment)
            is Destination.Authorization.SignIn -> navController.navigate(R.id.signInFragment)
            is Destination.Authorization.SignUp -> navController.navigate(R.id.signUpFragment)
            is Destination.Main.MainScreen -> navController.navigate(R.id.mainFragment)
            is Destination.Main.Profile -> navController.navigate(R.id.profileFragment)
            is Destination.Main.Settings -> navController.navigate(R.id.settingsFragment)
        }
    }
}