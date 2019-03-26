package cn.wsgwz.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger

class BlankFragmentBViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var simpleName : MutableLiveData<String> = MutableLiveData<String>().apply {
        value ="adsfaf "
    }

    override fun onCleared() {
        super.onCleared()
        Logger.t(TAG).d('a')
    }

    companion object {
        private const val TAG  = "BlankFragmentBViewModel"
    }
}
