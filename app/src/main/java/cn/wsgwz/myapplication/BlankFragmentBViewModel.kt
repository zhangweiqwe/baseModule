package cn.wsgwz.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.wsgwz.basemodule.utilities.LLog

class BlankFragmentBViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var simpleName : MutableLiveData<String> = MutableLiveData<String>().apply {
        value ="adsfaf "
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        private const val TAG  = "BlankFragmentBViewModel"
    }
}
