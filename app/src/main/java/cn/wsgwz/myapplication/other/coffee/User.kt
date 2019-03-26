package cn.wsgwz.baselibrary.other.coffee

import javax.inject.Inject

open class User  constructor(var name:String){
    override fun toString(): String {
        return "User(name='$name')"
    }
}