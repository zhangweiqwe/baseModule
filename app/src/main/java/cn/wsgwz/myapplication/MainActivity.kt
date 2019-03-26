package cn.wsgwz.myapplication

import android.Manifest
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import cn.wsgwz.basemodule.BaseConst
import cn.wsgwz.basemodule.CustomDisposableObserver
import cn.wsgwz.basemodule.adapters.BaseFragmentPagerAdapter
import cn.wsgwz.basemodule.interfaces.listeners.OnUserSelectListener
import cn.wsgwz.basemodule.adapters.UserBaseAdapter
import cn.wsgwz.basemodule.data.ProgressInfo
import cn.wsgwz.myapplication.data.BlogService
import cn.wsgwz.myapplication.data.TR
import cn.wsgwz.myapplication.other.coffee.*
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import javax.inject.Inject
import cn.wsgwz.basemodule.data.User
import cn.wsgwz.basemodule.utilities.manager.DownloadManager
import cn.wsgwz.basemodule.utilities.manager.FileManager
import cn.wsgwz.basemodule.utilities.manager.UserManager
import cn.wsgwz.basemodule.utilities.ApkUtil
import cn.wsgwz.basemodule.utilities.GlideUtil
import cn.wsgwz.basemodule.utilities.RetrofitUtil
import cn.wsgwz.basemodule.widgets.ProgressView
import cn.wsgwz.basemodule.widgets.suspension.SuspensionWindowManager
import com.wanglu.photoviewerlibrary.PhotoViewer
import io.reactivex.observers.DisposableObserver
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppBaseActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val blogService = BaseConst.RETROFIT.create(BlogService::class.java)

    private val rxPermissions = RxPermissions(this)


    @Inject
    internal lateinit var coffeeMaker: CoffeeMaker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Toolbar.get().into(this).title("看看").addMenu(TextView(this@MainActivity).apply {
            text = "fdsf"
            setOnClickListener { toast("tv") }
        }).addMenu(TextView(this@MainActivity).apply {
            text = "fdsf"
            setOnClickListener { toast("tv") }
        })



        view_pager.adapter = BaseFragmentPagerAdapter(supportFragmentManager).apply {
            addFragment(BlankFragmentB.newInstance()," 看看 ")
            addFragment(BlankFragment.newInstance("","")," 看看1范德萨发舒服 ")
            addFragment(BlankFragment.newInstance("","")," 看看2 ")
            addFragment(BlankFragment.newInstance("","")," 看看2 ")
        }
        /*table_layout.post {
            ViewUtil.setIndicator(table_layout,20,20)
        }*/
        table_layout.setupWithViewPager(view_pager)


        rxPermissions
            .request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)
            .subscribe { granted ->
                Logger.t(TAG).d(granted)

                if (granted) { // Always true pre-M
                    // I can control the camera now
                } else {
                    toast(R.string.permission_denied)
                    // Oups permission denied
                }
            }






        blogService.getTr(FormBody.Builder().add("ss", "ss").build())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CustomDisposableObserver<TR>() {

                override fun onNext(tr: TR) {
                    Logger.t(TAG).d(tr.toString())
                    simple_tv.text = tr.hashCode().toString()
                }

                override fun onError(e: Throwable) {
                    Logger.t(TAG).d(RetrofitUtil.getHint(e))
                }

            }.apply { compositeDisposable.add(this) })
        blogService.getTr2(FormBody.Builder().add("ss", "ss").build()).also {
            //it.cancel()
        }.enqueue(
            object : retrofit2.Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Logger.t(TAG).d("onFailure" + t.message)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Logger.t(TAG).d("onResponse")

                    simple_tv.text = response.body().toString()

                }

            }
        )



        DaggerCoffeeApp_CoffeeShop3.create().inject(this)
        Logger.t(TAG).d(coffeeMaker.brew().hashCode())


        //BaseConst.GSON.toJson("", MainActivity::class.java)


        simple_acactv.setAdapter(UserBaseAdapter(this, object : OnUserSelectListener {
            override fun onSelect(user: User) {
                user.id.also {
                    simple_acactv.setText(it)
                    simple_acactv.setSelection(it.length)
                }
            }

        }))

        UserManager.getInstance().also {
            if (it.getCurrentUser() == null) {

                Logger.t(TAG).d("it.getCurrentUser() == null")
                it.login("15923549211", "abc123", "bd82d2524f414627930bca9dc3dd5d2b")
            }
        }


        GlideUtil.bindImageFromUrl(image_view, BaseConst.SIMPLE_FILE_URL_2)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this, BaseConst.NOTIFICATION_CHANNEL_ID)

        val notifyId = notificationBuilder.hashCode()
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
        notificationBuilder.setContentTitle(getString(R.string.downloading))
        notificationBuilder.setContentText(BaseConst.SIMPLE_FILE_URL_2)
        notificationBuilder.setOngoing(true)


        var disposableObserver: DisposableObserver<ProgressInfo>? = null

        val progressView = ProgressView(context = this)
        val alertDialog =
            AlertDialog.Builder(this).setTitle(cn.wsgwz.basemodule.R.string.downloading).setView(progressView)
                .setCancelable(false)
                .setPositiveButton(R.string.cancel) { _, _ ->
                    disposableObserver?.dispose()
                    Handler().postDelayed({
                        notificationBuilder.setOngoing(false)
                        notificationBuilder.setContentTitle(getString(R.string.user_canceled))
                        notificationManager.notify(notifyId, notificationBuilder.build())
                    }, 200)
                }.create()
        //alertDialog.show()

        if (false)
            DownloadManager.getInstance()
                .download(
                    this,
                    Request.Builder().url(BaseConst.SIMPLE_FILE_URL_2).build(),
                    FileManager.UPDATE_FILE_NAME,
                    object : DisposableObserver<ProgressInfo>() {
                        override fun onComplete() {
                            notificationBuilder.setOngoing(false)
                            notificationBuilder.setContentTitle(getString(R.string.download_success))
                            notificationBuilder.setContentIntent(
                                ApkUtil.getInstallPendingIntent(
                                    this@MainActivity,
                                    DownloadManager.getFile(this@MainActivity, FileManager.UPDATE_FILE_NAME)?.apply {
                                        Logger.t(TAG).d("${exists()}")
                                    }
                                )
                            )

                            notificationManager.notify(notifyId, notificationBuilder.build())

                            alertDialog.setTitle(R.string.download_success)
                            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).also {
                                it.text = getString(R.string.install)
                                it.setOnClickListener {
                                    startActivity(ApkUtil.getInstallIntent(
                                        this@MainActivity,
                                        DownloadManager.getFile(
                                            this@MainActivity,
                                            FileManager.UPDATE_FILE_NAME
                                        )?.apply {
                                            Logger.t(TAG).d("${exists()}")
                                        }
                                    ))
                                }
                            }
                            alertDialog.dismiss()
                            //Logger.t(TAG).d("onComplete")
                        }

                        override fun onNext(t: ProgressInfo) {

                            notificationBuilder.setProgress(
                                (t.contentLength / 100).toInt(),
                                (t.bytesRead / 100).toInt(),
                                false
                            )
                            notificationManager.notify(
                                notifyId,
                                notificationBuilder.build().apply {
                                    flags = flags or Notification.FLAG_ONLY_ALERT_ONCE
                                })
                            progressView.updateProgress(t)
                            //Logger.t(TAG).d("onNext ${t.bytesRead} ${t.contentLength} ${Thread.currentThread().id}")
                        }

                        override fun onError(e: Throwable) {
                            notificationBuilder.setOngoing(false)
                            notificationBuilder.setContentTitle(getString(R.string.download_error))
                            notificationManager.notify(notifyId, notificationBuilder.build())
                            alertDialog.dismiss()
                            //Logger.t(TAG).d("onError ${e.let { it.javaClass.canonicalName +"${it is IOException}"+ it.message}} ${Thread.currentThread().id}")
                        }
                    }.apply {
                        disposableObserver = this
                        compositeDisposable.add(this)
                    }
                )


        /*startActivity(Intent(this, BaseWebViewActivity::class.java).apply {
            putExtra("title", "zr")
            putExtra("url", BaseConst.SIMPLE_URL)
            putExtra("windowTranslucentStatus", true)
        })*/

        /*QuickPopupBuilder.with(this)
                      .contentView(R.layout.simple_view)
                      .config( QuickPopupConfig()
                              .gravity(Gravity.RIGHT or Gravity.CENTER_VERTICAL)
                              .withClick(R.id.text_view) {

                              })
                      .show();*/


        SuspensionWindowManager.getInstance().init(this)

        //throw Exception("范德萨发")

        GlideUtil.bindImageFromUrl(image_view, BaseConst.SIMPLE_IMG_URL)
        image_view.setOnClickListener {
            PhotoViewer
                .setClickSingleImg(BaseConst.SIMPLE_IMG_URL, image_view)
                .setShowImageViewInterface(object : PhotoViewer.ShowImageViewInterface {
                    override fun show(iv: ImageView, url: String) {
                        GlideUtil.bindImageFromUrl(iv, url)
                    }
                })
                .start(this)
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
