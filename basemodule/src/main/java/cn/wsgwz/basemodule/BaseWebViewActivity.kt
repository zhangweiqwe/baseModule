package cn.wsgwz.basemodule

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.*
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import cn.wsgwz.basemodule.interfaces.BaseJsInterface
import cn.wsgwz.basemodule.utilities.AndroidBug5497Workaround
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.manager.UserManager
import cn.wsgwz.basemodule.utilities.WindowUtil
import cn.wsgwz.basemodule.widgets.ScrollWebView
import cn.wsgwz.basemodule.widgets.progressActivity.WebViewProgressConstraintLayout
import kotlinx.android.synthetic.main.activity_base_web_view.*
import kotlinx.android.synthetic.main.view_toolbar_layout.*
import java.util.HashMap

private const val TAG = "BaseWebViewActivity"

open class BaseWebViewActivity : BaseNetworkActivity() {


    private var tempRequestedOrientation = 0

    private var windowTranslucentStatus: Boolean = false


    private val userManager by lazy {
        UserManager.getInstance()
    }


    private val videoContainer by lazy {
        FrameLayout(this).apply {
            id = progress_layout.videoContainerId
            setBackgroundColor(Color.RED)
            progress_layout.addView(this, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }


    private var tempStatusBarColor = 0


    private fun WebViewProgressConstraintLayout.showContent(isShow: Boolean) {
        for (v in arrayOf(findViewById<View>(R.id.toolbar_parent_layout), web_view)) {

            LLog.d(TAG, "$isShow ${v.hashCode()}")
            v.visibility = isShow.let {
                if (it) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
            }
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowUtil.setStatusBarTransparent(this)
        WindowUtil.setTranslucentStatus(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_web_view)
        AndroidBug5497Workaround.assistActivity(this)

        tempStatusBarColor = window.statusBarColor
        tempRequestedOrientation = requestedOrientation


        onInitActionBar()



        windowTranslucentStatus = intent.getBooleanExtra("windowTranslucentStatus", false)
        if (windowTranslucentStatus) {

            web_view.also {
                it.layoutParams = (it.layoutParams as ConstraintLayout.LayoutParams).also {
                    it.topToTop = ConstraintSet.PARENT_ID
                }
            }

            progress_bar.also {
                it.layoutParams = (it.layoutParams as ConstraintLayout.LayoutParams).also {
                    it.topToTop = ConstraintSet.PARENT_ID
                }
            }
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

                LLog.d(TAG, "onShowCustomView ${view?.hashCode()}")
                progress_layout.showContent(false)
                supportActionBar?.hide()
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

                videoContainer.also {
                    it.visibility = View.VISIBLE
                    it.addView(view)
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                LLog.d(TAG, "onHideCustomView")
                supportActionBar?.show()
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                videoContainer.visibility = View.GONE
                videoContainer.removeAllViews()
                progress_layout.showContent(true)

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

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {

                LLog.d(TAG,"onJsConfirm$url $message $result")
                return super.onJsConfirm(view, url, message, result)
            }

            override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {

                LLog.d(TAG,"onJsPrompt$url $message $defaultValue $result")
                return super.onJsPrompt(view, url, message, defaultValue, result)
            }
        }


        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
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

            val color1 = tempStatusBarColor and 0x00ffffff
            web_view.setOnScrollChangeListener(object : ScrollWebView.OnScrollChangeListener {
                override fun onScrollChange(v: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                    if (height > scrollY) {
                        val color = 255 * scrollY / height shl 24 or color1
                        toolbar_parent_layout.setBackgroundColor(color)
                    } else {
                        toolbar_parent_layout.setBackgroundColor(tempStatusBarColor)
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
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.also {
            it.setBackgroundDrawable(null)
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
