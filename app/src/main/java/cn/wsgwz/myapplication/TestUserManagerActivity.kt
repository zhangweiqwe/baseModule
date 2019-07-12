package cn.wsgwz.myapplication

import android.os.Bundle
import android.text.TextUtils
import cn.wsgwz.basemodule.adapters.UserBaseAdapter
import cn.wsgwz.basemodule.data.User
import cn.wsgwz.basemodule.utilities.manager.UserManager
import kotlinx.android.synthetic.main.activity_test_user_manager.*

class TestUserManagerActivity : AppBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_user_manager)


        //for(i in 0..10)
        UserManager.login("${Math.random()}", StringBuilder().apply {
            append(1)
            for (i in 1..9) {
                append((Math.random() * 10).toInt())
            }
        }.toString(), "${Math.random()}")


        user_name_auto_text.setAdapter(UserBaseAdapter(this, object : UserBaseAdapter.OnUserSelectListener {
            override fun onSelect(user: User) {
                user_name_auto_text.setText(user.phone)
                password_text.setText(user.password)

                user.phone?.also { phone ->
                    user_name_auto_text.setSelection(phone.length)
                }
            }

        }))
    }
}
