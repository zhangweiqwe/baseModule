package cn.wsgwz.basemodule.widgets.suspension

import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.recyclerview.widget.RecyclerView
import cn.wsgwz.baselibrary.retrofit.bean.RequestData
import cn.wsgwz.baselibrary.retrofit.bean.ResponseData
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NetworkDataManager private constructor() {

    interface OnResponseItemDataChangeListener {
        fun onResponseItemDataChange(id: String)
    }

    private val listeners = ArrayList<OnResponseItemDataChangeListener>()

    private val requestData: ArrayList<RequestData> = ArrayList()
    private val responseMap = HashMap<String, ResponseData>()

    private var recyclerView: RecyclerView? = null

    fun setRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = recyclerView
    }


    private val handle = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (requestData.size > MAX_LENGTH) {
                requestData.clear()
                recyclerView?.adapter?.notifyDataSetChanged()
            }

            when (msg.what) {
                MSG_WHAT_ADD_REQUEST_DATA -> {
                    requestData.add(msg.obj as RequestData)
                    recyclerView?.also {
                        it.smoothScrollToPosition(requestData.size)
                        it.adapter?.notifyItemInserted(requestData.size)
                    }

                    //Logger.t(TAG).d("$recyclerView")
                }
                MSG_WHAT_ADD_RESPONSE_DATA -> {
                    (msg.obj as ResponseData).also {
                        responseMap[it.id] = it
                    }
                }

                MSG_WHAT_ON_RESPONSE_ITEM_DATA_CHANGE -> {
                    val id = msg.obj as String
                    listeners.forEach {
                        it.onResponseItemDataChange(id)
                    }
                }
                MSG_WHAT_CLEAR_ALL->{
                    requestData.clear()
                    recyclerView?.adapter?.notifyDataSetChanged()
                }
            }


        }
    }

    fun clearAll(){
        handle.sendEmptyMessage(MSG_WHAT_CLEAR_ALL)
    }

    fun getRequestData(): MutableList<RequestData> {
        return Collections.unmodifiableList(requestData)
    }

    fun getResponseData(): Map<String, ResponseData> {
        return Collections.unmodifiableMap(responseMap)
    }

    fun addRequestData(requestData: RequestData) {
        handle.sendMessage(Message.obtain().apply {
            what = MSG_WHAT_ADD_REQUEST_DATA
            obj = requestData
        })
    }

    fun addResponseData(responseData: ResponseData) {
        handle.sendMessage(Message.obtain().apply {
            what = MSG_WHAT_ADD_RESPONSE_DATA
            obj = responseData
        })

    }


    fun addOnResponseItemDataChangeListener(onResponseItemDataChangeListener: OnResponseItemDataChangeListener) =
            listeners.add(onResponseItemDataChangeListener)

    fun removeOnResponseItemDataChangeListener(onResponseItemDataChangeListener: OnResponseItemDataChangeListener) =
            listeners.remove(onResponseItemDataChangeListener)

    fun notifyOnResponseItemDataChange(id: String) {
        handle.sendMessage(Message.obtain().apply {
            what = MSG_WHAT_ON_RESPONSE_ITEM_DATA_CHANGE
            obj = id
        })
    }

    companion object {

        private const val TAG = "NetworkDataManager"

        private const val MAX_LENGTH = 100

        private const val MSG_WHAT_ADD_REQUEST_DATA = 1000
        private const val MSG_WHAT_ADD_RESPONSE_DATA = 1001
        private const val MSG_WHAT_ON_RESPONSE_ITEM_DATA_CHANGE = 1002
        private const val MSG_WHAT_CLEAR_ALL = 1003
        // For Singleton instantiation
        @Volatile
        private var instance: NetworkDataManager? = null

        @JvmStatic
        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: NetworkDataManager().also { instance = it }
                }
    }
}
