package cn.wsgwz.basemodule

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import cn.wsgwz.basemodule.interfaces.BaseJsInterface
import cn.wsgwz.basemodule.interfaces.BaseWindowInterface
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.manager.UserManager
import cn.wsgwz.basemodule.utilities.WindowUtil
import cn.wsgwz.basemodule.widgets.ScrollWebView
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressConstraintLayout
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout
import java.util.HashMap
import javax.inject.Inject

open class BaseWebViewActivity : BaseNetworkActivity() {

    companion object {
        private val TAG by lazy { BaseWebViewActivity::class.java.simpleName }
    }

    private var tempRequestedOrientation = 0

    private var windowTranslucentStatus: Boolean = false

    protected lateinit var toolbar_parent_cl: ConstraintLayout
    private lateinit var progress_layout: ProgressConstraintLayout
    private lateinit var web_view: ScrollWebView
    private lateinit var video_container_fl: FrameLayout
    private lateinit var progress_bar: ProgressBar


    private val userManager by lazy {
        UserManager.getInstance()
    }





    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_web_view)

        toolbar_parent_cl = findViewById(R.id.toolbar_parent_cl)
        progress_layout = findViewById(R.id.progress_layout)
        web_view = findViewById(R.id.web_view)
        video_container_fl = findViewById(R.id.video_container_fl)
        progress_bar = findViewById(R.id.progress_bar)

        tempRequestedOrientation = requestedOrientation
        onInitActionBar()
        windowTranslucentStatus = intent.getBooleanExtra("windowTranslucentStatus", false)
        if (windowTranslucentStatus) {
            progress_layout.layoutParams = (progress_layout.layoutParams as ConstraintLayout.LayoutParams).also {
                it.topToTop = ConstraintSet.PARENT_ID
            }


            /*window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))*/

        }
        web_view.settings.also {
            //webSettings.setSupportZoom(true);
            it.displayZoomControls = false
            it.builtInZoomControls = true
            it.useWideViewPort = true
            it.loadWithOverviewMode = true
            it.javaScriptEnabled = true
        }
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View?, requestedOrientation: Int, callback: CustomViewCallback?) {
                super.onShowCustomView(view, requestedOrientation, callback)
                onShowCustomView(view, callback)
            }

            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)

                LLog.d(TAG, "onShowCustomView")
                progress_layout.visibility = View.INVISIBLE
                supportActionBar?.hide()
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

                video_container_fl.also {
                    it.visibility = View.VISIBLE
                    it.addView(view)
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                supportActionBar?.show()
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                video_container_fl.visibility = View.GONE
                progress_layout.visibility = View.VISIBLE

                requestedOrientation = tempRequestedOrientation
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress != 100) {
                    progress_bar.visibility = View.VISIBLE
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progress_bar.setProgress(newProgress, true)
                    } else {
                        progress_bar.progress = newProgress
                    }

                } else {
                    progress_bar.visibility = View.INVISIBLE
                }
            }
        }


        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //progress_layout.showContent()
                LLog.d(TAG, "onPageFinished")
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                progress_layout.showContent()
                LLog.d(TAG, "shouldOverrideUrlLoading")
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                LLog.d(TAG, "onReceivedError")
                showError()
            }

            /*override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
            }*/
        }
        web_view.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        web_view.addJavascriptInterface(BaseJsInterface(this), "app")


        val paramsMap = HashMap<String, String>()
        userManager.getCurrentUser()?.token?.also {
            paramsMap["token"] = it
        }
        paramsMap["app-user-agent"] = "android"

        val uri = Uri.parse(intent.getStringExtra("url"))
        val builder = uri.buildUpon()
        for ((key, value) in paramsMap) {
            builder.appendQueryParameter(key, value)
        }

        web_view.loadUrl(builder.build().toString().also {
            LLog.d(TAG, it)
        }, paramsMap)



        if (windowTranslucentStatus) {
            val height = WindowUtil.getStatusBarHeight(this) + supportActionBar.let {
                it?.height ?: 0
            }
            val color0 = ContextCompat.getColor(this@BaseWebViewActivity, R.color.colorPrimary)

            val color1 = color0 and 0x00ffffff
            web_view.setOnScrollChangeListener(object : ScrollWebView.OnScrollChangeListener {
                override fun onScrollChange(v: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                    if (height > scrollY) {
                        val color = 255 * scrollY / height shl 24 or color1
                        toolbar_parent_cl.setBackgroundColor(color)
                        //supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
                    } else {
                        toolbar_parent_cl.setBackgroundColor(color0)
                        //supportActionBar?.setBackgroundDrawable(ColorDrawable(color0))
                    }

                }
            })
        }


    }


    override fun onRefresh() {
        super.onRefresh()
        web_view.reload()
        progress_layout.showContent()
    }

    private fun showError() {
        progress_layout.showError { onRefresh() }
    }

    open fun onInitActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.also {
            //it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.title = intent.getStringExtra("title")
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

    override fun onPause() {
        super.onPause()
        web_view.onPause()
        web_view.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        web_view.onResume()
        web_view.resumeTimers()
    }

    override fun onDestroy() {
        super.onDestroy()
        (window.decorView as ViewGroup).removeAllViews()
        web_view.destroy()
    }

    override fun onBackPressed() {
        web_view.also {
            if (it.canGoBack()) {
                it.goBack()
                progress_layout.showContent()
            } else {
                super.onBackPressed()
            }
        }
    }
}
