package cn.wsgwz.basemodule.utilities

import android.util.Log
import cn.wsgwz.basemodule.BuildConfig

object LLog {
    private const val MAX_LENGTH = 4000
    private val IS_SHOW_LOG = BuildConfig.DEBUG

    private interface LogListener {
        fun log(tag: String?, msg: String?)
    }

    private fun log(tag: String?, msg: String?, logListener: LogListener) {

        if (!IS_SHOW_LOG || tag == null) {
            return
        }
        if (msg == null || msg.length <= MAX_LENGTH) {
            logListener.log(tag, msg)
        } else {
            var index = 0
            var finalString: String
            while (index < msg.length) {
                if (msg.length <= index + MAX_LENGTH) {
                    finalString = msg.substring(index)
                } else {
                    finalString = msg.substring(index, msg.length )
                }
                index += MAX_LENGTH
                logListener.log(tag, finalString)
            }
        }
    }


    @JvmStatic
    fun v(tag: String?, msg: String?) {
        log(tag, msg, object : LogListener {
            override fun log(tag: String?, msg: String?) {
                Log.d(tag, msg)
            }
        })
    }

    @JvmStatic
    fun d(tag: String?, msg: String?) {
        log(tag, msg, object : LogListener {
            override fun log(tag: String?, msg: String?) {
                Log.d(tag, msg)
            }
        })
    }


    @JvmStatic
    fun i(tag: String?, msg: String?) {
        log(tag, msg, object : LogListener {
            override fun log(tag: String?, msg: String?) {
                Log.i(tag, msg)
            }
        })
    }


    @JvmStatic
    fun w(tag: String?, msg: String?) {
        log(tag, msg, object : LogListener {
            override fun log(tag: String?, msg: String?) {
                Log.w(tag, msg)
            }
        })
    }


    @JvmStatic
    fun e(tag: String?, msg: String?) {
        log(tag, msg, object : LogListener {
            override fun log(tag: String?, msg: String?) {
                Log.e(tag, msg)
            }
        })
    }


    @JvmStatic
    fun a(tag: String?, msg: String?) {
        log(tag, msg, object : LogListener {
            override fun log(tag: String?, msg: String?) {
                Log.w(tag, msg)
            }
        })
    }






}