package yan.zubritskiy.templatemvvm.ui.navigation

sealed class Destination {
    sealed class Authorization: Destination() {
        object Onboarding: Authorization()
        object SignIn: Authorization()
        object SignUp: Authorization()
    }
    sealed class Main: Destination() {
        object MainScreen: Main()
        object Profile: Main()
        object Settings: Main()
    }
}