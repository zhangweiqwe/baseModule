package cn.wsgwz.basemodule.utilities.retrofit


class ServerException(val code: Int, message: String?) : Throwable( message) {

}