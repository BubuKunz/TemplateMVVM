package yan.zubritskiy.templatemvvm.ui.auth

import android.os.Bundle
import android.view.View
import yan.zubritskiy.templatemvvm.R
import yan.zubritskiy.templatemvvm.databinding.FragmentSignUpBinding
import yan.zubritskiy.templatemvvm.ui.base.BaseFragment
import yan.zubritskiy.templatemvvm.ui.base.viewBinding
import yan.zubritskiy.templatemvvm.ui.navigation.Destination

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainBtn.setOnClickListener {
            navigator.goTo(Destination.Main.MainScreen)
        }
    }

}