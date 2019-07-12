package cn.wsgwz.baselibrary.widgets.suspension


import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.widgets.suspension.NetworkDataManager
import cn.wsgwz.basemodule.widgets.suspension.NetworkDataAdapter
import cn.wsgwz.basemodule.widgets.suspension.bean.SuspensionWindowBean
import kotlinx.coroutines.runBlocking


/**
 * Created by zhongxiang.huang on 2017/6/23.
 */
private const val TAG = "LogSuspensionWindow"

class LogSuspensionWindow private constructor(private val context: Context) : SuspensionWindow(context),
        View.OnTouchListener, View.OnClickListener, View.OnLongClickListener {

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dismiss_iv -> {
                BaseApplication.getPreferences().edit().putBoolean("is_show_network_data_suspension_window", false).apply()
                SuspensionWindowManager.getInstance().hide(SuspensionWindowType.LOG)
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        return when (v.id) {
            R.id.dismiss_iv -> {
                networkDataManager.clearAll()
                true
            }
            else -> {
                false
            }
        }


    }


    private val wmLayoutParams: WindowManager.LayoutParams
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager


    private var wWidth: Int = 0
    private var wHeight: Int = 0
    private var vWidth: Int = 0
    private var vHeight: Int = 0

    private var x: Float = 0F
    private var y: Float = 0F
    private var rawX: Float = 0F
    private var rawY: Float = 0F


    private val parent_fl: FrameLayout
    private val data_rv: RecyclerView
    private val drag_v: View

    private val networkDataManager = NetworkDataManager.getInstance()

    init {
        wmLayoutParams = WindowManager.LayoutParams().apply {
            width = DensityUtil.dp2px(140.286f).toInt().apply {
                wWidth = this
            }
            height = DensityUtil.dp2px(227f).toInt().apply {
                wHeight = this
            }
            windowAnimations = R.style.Zoom

            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0新特性
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                flags = flags or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
            }
            gravity = Gravity.TOP or Gravity.LEFT
        }


        LayoutInflater.from(context).inflate(R.layout.view_suspension_window, null).also { parentView ->
            parentView.findViewById<View>(R.id.dismiss_iv).apply {
                setOnClickListener(this@LogSuspensionWindow)
                setOnLongClickListener(this@LogSuspensionWindow)
            }

            parent_fl = parentView.findViewById(R.id.parent_fl)
            data_rv = parentView.findViewById(R.id.data_rv)
            data_rv.also {
                networkDataManager.setRecyclerView(it)

                it.adapter = NetworkDataAdapter(context).apply {
                }

                it.layoutManager = LinearLayoutManager(context).also { layoutManager ->
                    layoutManager.reverseLayout = true
                }
                it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                    setDrawable(ShapeDrawable().apply {
                        paint.color = Color.WHITE
                        intrinsicHeight = DensityUtil.dp2px(0.2f).toInt()
                    })
                })
            }

            drag_v = parentView.findViewById(R.id.drag_v)
            drag_v.setOnTouchListener(this)
            drag_v.post {
                vWidth = drag_v.width
                vHeight = drag_v.height
            }

        }

    }


    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {


        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                x = motionEvent.x
                y = motionEvent.y
                rawX = motionEvent.rawX
                rawY = motionEvent.rawY
            }
            MotionEvent.ACTION_MOVE -> {

                rawX = motionEvent.rawX
                rawY = motionEvent.rawY
                wmLayoutParams.also {
                    it.x = (rawX - x).toInt()
                    it.y = (rawY - wHeight + (vHeight - y)).toInt()
                    windowManager.updateViewLayout(parent_fl, it)

                    runBlocking {
                        suspensionWindowRepository.update(
                                SuspensionWindowBean(TAG, it.x, it.y)
                        )
                    }
                }


                /*  wmLayoutParams.also {
                      it.x = rawX.toInt()
                      it.y = rawY.toInt()
                      windowManager.updateViewLayout(parentView, it)
                  }*/


            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return false
    }


    override fun show() {
        if (parent_fl.parent == null) {

            runBlocking {
                val suspensionWindow = suspensionWindowRepository.getSuspensionWindow(TAG)
                if (suspensionWindow == null) {
                    val insertSuspensionWindow = SuspensionWindowBean(
                            TAG,
                            DensityUtil.dp2px(138f).toInt(),
                            DensityUtil.dp2px(85.284f).toInt()
                    )
                    wmLayoutParams.x = insertSuspensionWindow.x
                    wmLayoutParams.y = insertSuspensionWindow.y
                    suspensionWindowRepository.insert(insertSuspensionWindow)
                } else {
                    wmLayoutParams.x = suspensionWindow.x
                    wmLayoutParams.y = suspensionWindow.y
                }
            }

            windowManager.addView(parent_fl, wmLayoutParams)


        }
    }

    override fun hide() {
        if (parent_fl.parent != null) {
            windowManager.removeView(parent_fl)
            networkDataManager.setRecyclerView(null)
        }
    }

    companion object {

        fun create(context: Context): LogSuspensionWindow {
            return LogSuspensionWindow(context)
        }

    }

}
