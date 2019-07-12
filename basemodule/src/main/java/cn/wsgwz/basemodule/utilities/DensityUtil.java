package cn.wsgwz.basemodule.utilities;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 * 
 *
 * context.getResources().getDisplayMetrics()
 */
public class DensityUtil
{
	private DensityUtil(){}
	/**
	 * dp转px
	 *
	 * @param dpVal
	 * @return
	 */
	public static float dp2px( float dpVal)
	{
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, Resources.getSystem().getDisplayMetrics());
	}


	/**
	 * sp转px
	 *
	 * @param spVal
	 * @return
	 */
	public static int sp2px( float spVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, Resources.getSystem().getDisplayMetrics());
	}

	/**
	 * px转dp
	 *
	 * @param pxVal
	 * @return
	 */
	public static float px2dp( float pxVal)
	{
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 *
	 * @param pxVal
	 * @return
	 */
	public static float px2sp( float pxVal)
	{
		return (pxVal / Resources.getSystem().getDisplayMetrics().scaledDensity);
	}

}
