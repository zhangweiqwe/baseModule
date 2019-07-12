package cn.wsgwz.basemodule.utilities.retrofit

enum class Code(val code:Int){
    SUCCESS(0),FAILURE(1),EXCEPTION(-1),NO_LOGIN(2);
    //SUCCESS(0),EXCEPTION(-1),BUSINESS_LOGIC_ERROR(1),NOT_LOGGED_IN(2)

    //success(0, "操作成功"), failure(1, "操作失败"), exception(-1, "系统繁忙"), noLogin(2, "未登录");


    companion object{
        fun getCode(code:Int):Code?{
            Code.values().forEach {
                if(code==it.code){
                    return it
                }
            }
            return null
        }
    }
}