package cn.wsgwz.basemodule.utilities.manager

import cn.wsgwz.basemodule.interfaces.listeners.OnConnectivityChangeListener


class ConnectivityChangeListenerManager private constructor() {
    private val listeners = ArrayList<OnConnectivityChangeListener>()

    fun unregister(onConnectivityChangeListener: OnConnectivityChangeListener) =
        listeners.remove(onConnectivityChangeListener)

    fun register(onConnectivityChangeListener: OnConnectivityChangeListener) = listeners.add(onConnectivityChangeListener)

    fun notifyConnectivityChange() {
        listeners.forEach {
            it.onConnectivityChange()
        }
    }
    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: ConnectivityChangeListenerManager? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ConnectivityChangeListenerManager().also { instance = it }
            }
    }
}