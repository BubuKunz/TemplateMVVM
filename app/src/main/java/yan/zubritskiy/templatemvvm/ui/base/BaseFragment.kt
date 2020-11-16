package yan.zubritskiy.templatemvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import yan.zubritskiy.templatemvvm.ui.navigation.Navigator

abstract class BaseFragment(@LayoutRes private val layoutId: Int) : Fragment() {

    protected val navigator: Navigator by inject {
        parametersOf(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }
}