package yan.zubritskiy.templatemvvm.ui.main

import android.os.Bundle
import android.view.View
import yan.zubritskiy.templatemvvm.R
import yan.zubritskiy.templatemvvm.databinding.FragmentSettingsBinding
import yan.zubritskiy.templatemvvm.ui.base.BaseFragment
import yan.zubritskiy.templatemvvm.ui.base.viewBinding
import yan.zubritskiy.templatemvvm.ui.navigation.Destination

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutBtn.setOnClickListener {
            navigator.goTo(Destination.Authorization.Onboarding)
        }
    }
}