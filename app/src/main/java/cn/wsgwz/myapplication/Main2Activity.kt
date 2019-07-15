package cn.wsgwz.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.wsgwz.baselibrary.permission.PermissionV2
import cn.wsgwz.baselibrary.widgets.suspension.SuspensionWindowManager
import cn.wsgwz.basemodule.other.SimpleViewHolder
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.NetworkUtil
import cn.wsgwz.common.ToolbarManager
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.list_item_test.view.*
import javax.inject.Inject
import javax.inject.Named

class Main2Activity : AppBaseActivity() {

    companion object {
        private const val TAG = "Main2Activity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        SuspensionWindowManager.getInstance().init(this)

        ToolbarManager.get().into(this).title("测试")


        //LLog.d(TAG,s)

        class TestItem(val name: String?, val onClickListener: View.OnClickListener?)

        val data = ArrayList<TestItem>()

        data.apply {
            add(TestItem("下载文件及进度", View.OnClickListener {
                //startActivity(Intent(this@Main2Activity, TestDownloadActivity::class.java))
                val permissionV2 = PermissionV2.STORAGE
                RxPermissions(this@Main2Activity).request(*permissionV2.permissions)
                        .subscribe {
                            if (it) {
                                startActivity(Intent(this@Main2Activity, TestDownloadActivity::class.java))
                            } else {
                                toast(permissionV2.getNoPermissionDescription())
                            }
                        }


            }))


            add(TestItem("上传文件", View.OnClickListener {
                val permissionV2 = PermissionV2.STORAGE
                RxPermissions(this@Main2Activity).request(*permissionV2.permissions)
                        .subscribe {
                            if (it) {
                                startActivity(Intent(this@Main2Activity, TestUploadActivity::class.java))
                            } else {
                                toast(permissionV2.getNoPermissionDescription())
                            }
                        }

            }))

            add(TestItem("测试用户管理器", View.OnClickListener {
                startActivity(Intent(this@Main2Activity, TestUserManagerActivity::class.java))
            }))

            add(TestItem("数据加载中测试1", View.OnClickListener {
                showLoading(true)
                it.postDelayed({
                    dismissLoading()
                }, 10 * 1000)
            }))

            add(TestItem("ProgressLayout", View.OnClickListener {
                startActivity(Intent(this@Main2Activity,TestProgressLayoutActivity::class.java))
            }))

        }
        content_rv.layoutManager = LinearLayoutManager(this)
        content_rv.adapter = object : RecyclerView.Adapter<SimpleViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleViewHolder(LayoutInflater.from(this@Main2Activity).inflate(R.layout.list_item_test, parent, false))
            override fun getItemCount() = data.size

            override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
                holder.itemView.apply {
                    data[position].apply {
                        name_text.also {
                            it.text = name
                            it.setOnClickListener(onClickListener)
                        }

                    }
                }
            }

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                //finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
