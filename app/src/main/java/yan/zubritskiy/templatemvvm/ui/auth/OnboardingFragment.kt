package yan.zubritskiy.templatemvvm.ui.auth

import android.os.Bundle
import android.view.View
import yan.zubritskiy.templatemvvm.R
import yan.zubritskiy.templatemvvm.databinding.FragmentOnboardingBinding
import yan.zubritskiy.templatemvvm.ui.base.BaseFragment
import yan.zubritskiy.templatemvvm.ui.base.viewBinding
import yan.zubritskiy.templatemvvm.ui.navigation.Destination

class OnboardingFragment : BaseFragment(R.layout.fragment_onboarding) {
    private val binding by viewBinding(FragmentOnboardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInBtn.setOnClickListener {
            navigator.goTo(Destination.Authorization.SignIn)
        }
    }
}