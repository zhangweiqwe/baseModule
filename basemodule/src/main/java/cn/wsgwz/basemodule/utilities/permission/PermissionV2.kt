package cn.wsgwz.baselibrary.permission

import android.Manifest
import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.R

enum class PermissionV2 constructor(private val description: String, val permissions: Array<String>) {

    CALENDAR(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[0], arrayOf(Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR)),
    CAMERA(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[1], arrayOf(Manifest.permission.CAMERA)),
    CONTACTS(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[2], arrayOf(Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS)),
    LOCATION(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[3], arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)),
    MICROPHONE(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[4], arrayOf(Manifest.permission.RECORD_AUDIO)),
    PHONE(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[5], arrayOf(Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS)),
    SENSORS(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[6], arrayOf(Manifest.permission.BODY_SENSORS)),
    SMS(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[7], arrayOf(Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS)),
    STORAGE(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[8], arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)),
    INSTALL(BaseApplication.getInstance().resources.getStringArray(R.array.permission_description)[9], arrayOf(Manifest.permission.REQUEST_INSTALL_PACKAGES));


    fun getNoPermissionDescription(): String = BaseApplication.getInstance().let {
        String.format(it.getString(R.string.permission_denied_format), this.description)
    }

}