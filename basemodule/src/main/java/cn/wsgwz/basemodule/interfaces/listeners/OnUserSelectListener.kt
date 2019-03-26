package cn.wsgwz.basemodule.interfaces.listeners

import cn.wsgwz.basemodule.data.User

interface OnUserSelectListener {
    fun onSelect(user: User)
}