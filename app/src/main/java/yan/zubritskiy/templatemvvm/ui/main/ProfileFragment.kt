package yan.zubritskiy.templatemvvm.ui.main

import android.os.Bundle
import android.view.View
import yan.zubritskiy.templatemvvm.R
import yan.zubritskiy.templatemvvm.databinding.FragmentProfileBinding
import yan.zubritskiy.templatemvvm.ui.base.BaseFragment
import yan.zubritskiy.templatemvvm.ui.base.viewBinding
import yan.zubritskiy.templatemvvm.ui.navigation.Destination

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingsBtn.setOnClickListener {
            navigator.goTo(Destination.Main.Settings)
        }
    }
}