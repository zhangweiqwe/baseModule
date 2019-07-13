package cn.wsgwz.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import cn.wsgwz.basemodule.BaseConst
import cn.wsgwz.basemodule.adapters.BaseFragmentPagerAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import cn.wsgwz.basemodule.utilities.*
import com.wanglu.photoviewerlibrary.PhotoViewer


class MainActivity : AppBaseActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }




    /*@Inject
    internal lateinit var coffeeMaker: CoffeeMaker*/


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


    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.simple_menu, menu)
        return true
    }*/


}
