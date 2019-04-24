package cn.wsgwz.basemodule

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.wsgwz.basemodule.data.RequestData
import cn.wsgwz.basemodule.data.ResponseData
import cn.wsgwz.basemodule.utilities.manager.NetworkDataManager
import cn.wsgwz.basemodule.utilities.GsonUtil
import cn.wsgwz.basemodule.utilities.OkHttpUtil
import kotlinx.android.synthetic.main.activity_network_data.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

class NetworkDataActivity : BaseActivity(), NetworkDataManager.OnResponseItemDataChangeListener {


    private val networkDataManager = NetworkDataManager.getInstance()
    private lateinit var requestData: RequestData
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseAppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_data)
        requestData = intent.getParcelableExtra<RequestData>("requestData").also {
            request_url_et.setText(it.uri.toString())
        }
        networkDataManager.addOnResponseItemDataChangeListener(this)

        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
        }


        initContent(networkDataManager.getResponseData()[requestData.id])


    }

    private fun initContent(responseData: ResponseData?) {
        responseData?.also {
            if (it == null) {
                progress_bar.visibility = View.VISIBLE
            } else {
                progress_bar.visibility = View.GONE
                response_data_et.setText(toFormatStr(it.byteArray))
                supportActionBar?.title = it.code.toString()
            }
        }
    }

    private fun reSend() {
        progress_bar.visibility = View.VISIBLE
        BaseConst.OK_HTTP_CLIENT.newCall(Request.Builder().tag(this).url(requestData.uri.toString()).build())
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    progress_bar.post {
                        progress_bar.visibility = View.GONE
                        response_data_et.setText(e.message)
                        supportActionBar?.title = null
                    }
                }

                override fun onResponse(call: Call, response: Response) {

                    val responseData = ResponseData(requestData.id, response.body()?.bytes(), response.code())

                    progress_bar.post {
                        initContent(responseData)
                    }
                }

            })
    }

    override fun onResponseItemDataChange(id: String) {
        if (requestData.id == id) {
            initContent(networkDataManager.getResponseData()[id])
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        OkHttpUtil.cancel(BaseConst.OK_HTTP_CLIENT, this)
        networkDataManager.removeOnResponseItemDataChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.network_data, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.resend -> {
                reSend()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }

        }
    }

    private fun toFormatStr(byteArray: ByteArray?): String? {
        return byteArray?.let { byteArray ->
            String(byteArray).let {

                try {
                    GsonUtil.toPrettyFormat(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                    it
                }
                /*if (GsonUtil.isJson(it)) {
                    GsonUtil.toPrettyFormat(it)
                } else {
                    it
                }*/
            }
        }


    }
}
