package yan.zubritskiy.templatemvvm.ui.main

import android.os.Bundle
import android.view.View
import yan.zubritskiy.templatemvvm.R
import yan.zubritskiy.templatemvvm.databinding.FragmentMainBinding
import yan.zubritskiy.templatemvvm.ui.base.BaseFragment
import yan.zubritskiy.templatemvvm.ui.base.viewBinding
import yan.zubritskiy.templatemvvm.ui.navigation.Destination

class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileBtn.setOnClickListener {
            navigator.goTo(Destination.Main.Profile)
        }
    }
}