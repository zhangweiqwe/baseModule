package cn.wsgwz.myapplication.widgets

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.utilities.DrawableUtils
import android.graphics.*


class StatisticsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas)

      /*  val width = width
        val height = height*/

       /* val paint = Paint()
        //canvas.drawRoundRect(2.5f, 2.5f, width-2.5f, height-2.5f, 20f, 20f, paint)

//ContextCompat.getDrawable(context,R.drawable.statistic_rect_bg)
        val bitmap =
            DrawableUtils.drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.statistic_rect_bg)?.also {
                //it.setBounds(0, 0, DensityUtil.dp2px(context, 20f), DensityUtil.dp2px(context, 380f))

            })
        //BitmapFactory.decodeResource(resources,R.drawable.simple_img)
        canvas.drawBitmap(bitmap, 20f, 20f, paint)*/

        val paint = Paint()
//两个坐标形成变量，规定了渐变的方向和间距大小，着色器为镜像
        val linearGradient = LinearGradient(0f, 0f, 0f, DensityUtil.dp2px(context,120f), Color.RED, Color.RED, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        paint.shader = linearGradient
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = DensityUtil.dp2px(context,2f)
        /*paint.strokeWidth = 50f
        canvas.drawLine(0f, measuredHeight / 2f, measuredWidth.toFloat(), measuredHeight / 2f, paint)*/

        //canvas.drawRoundRect(RectF(2.5f, 2.5f, width-2.5f, height-2.5f), 20f, 20f, paint)
        canvas.drawRoundRect(RectF(DensityUtil.dp2px(context,30f), 0f,  DensityUtil.dp2px(context,130f),  DensityUtil.dp2px(context,180f)), 20f, 20f, paint)

    }
}