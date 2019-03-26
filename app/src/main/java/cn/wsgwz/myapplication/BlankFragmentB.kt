package cn.wsgwz.myapplication

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import cn.wsgwz.basemodule.BaseFragment
import cn.wsgwz.myapplication.databinding.BlankFragmentbFragmentBinding


class BlankFragmentB : BaseFragment() {

    companion object {
        fun newInstance() = BlankFragmentB()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val blankFragmentBViewModel = ViewModelProviders.of(this@BlankFragmentB).get(BlankFragmentBViewModel::class.java)

        val binding = DataBindingUtil.inflate<BlankFragmentbFragmentBinding>(
            inflater, R.layout.blank_fragmentb_fragment, container, false).apply {
            viewModel =  blankFragmentBViewModel
            setLifecycleOwner(this@BlankFragmentB)

        }
        blankFragmentBViewModel.simpleName.value = "fsadfas"




        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

}
