package yan.zubritskiy.templatemvvm.ui.auth

import android.os.Bundle
import android.view.View
import yan.zubritskiy.templatemvvm.R
import yan.zubritskiy.templatemvvm.databinding.FragmentSignInBinding
import yan.zubritskiy.templatemvvm.ui.base.BaseFragment
import yan.zubritskiy.templatemvvm.ui.base.viewBinding
import yan.zubritskiy.templatemvvm.ui.navigation.Destination

class SignInFragment: BaseFragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpBtn.setOnClickListener {
            navigator.goTo(Destination.Authorization.SignUp)
        }
    }
}