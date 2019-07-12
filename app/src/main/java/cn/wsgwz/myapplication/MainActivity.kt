package cn.wsgwz.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Observable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import cn.wsgwz.basemodule.BaseConst
import cn.wsgwz.basemodule.adapters.BaseFragmentPagerAdapter
import cn.wsgwz.basemodule.interfaces.listeners.OnUserSelectListener
import cn.wsgwz.basemodule.adapters.UserBaseAdapter
import cn.wsgwz.myapplication.data.BlogService
import cn.wsgwz.myapplication.other.coffee.*
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import cn.wsgwz.basemodule.data.User
import cn.wsgwz.basemodule.utilities.*
import cn.wsgwz.basemodule.utilities.manager.UserManager
import com.wanglu.photoviewerlibrary.PhotoViewer


class MainActivity : AppBaseActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val blogService = BaseConst.RETROFIT.create(BlogService::class.java)

    private val rxPermissions = RxPermissions(this)


    @Inject
    internal lateinit var coffeeMaker: CoffeeMaker


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







        view_pager.adapter = BaseFragmentPagerAdapter(supportFragmentManager).apply {
            addFragment(BlankFragmentB.newInstance(), " 看看 ")
            addFragment(BlankFragment.newInstance("", ""), " 看看1范德萨发舒服 ")
            addFragment(BlankFragment.newInstance("", ""), " 看看2 ")
            addFragment(BlankFragment.newInstance("", ""), " 看看2 ")
        }
        /*table_layout.post {
            ViewUtil.setIndicator(table_layout,20,20)
        }*/
        table_layout.setupWithViewPager(view_pager)



        GlideUtil.bindImageFromUrl(image_view, BaseConst.Url.IMG_URL)





        //throw Exception("范德萨发")

        GlideUtil.bindImageFromUrl(image_view, BaseConst.Url.IMG_URL)
        image_view.setOnClickListener {
            PhotoViewer
                    .setClickSingleImg(BaseConst.Url.IMG_URL, image_view)
                    .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                        override fun show(iv: ImageView, url: String) {
                            GlideUtil.bindImageFromUrl(iv, url)
                        }
                    })
                    .start(this)
        }


        /*Logger.t(TAG).d(DownloadsUtil.downLoadMangerIsEnable(this))

        DownloadsUtil.openDownloadSetting(this)*/

    }


    override fun onConnectivityChange() {
        super.onConnectivityChange()

        if (NetworkUtil.isNetworkActive(this)) {
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_menu, menu)
        return true
    }*/


}
