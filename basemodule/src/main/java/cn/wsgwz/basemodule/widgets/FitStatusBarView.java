package cn.wsgwz.basemodule.widgets;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;


public class FitStatusBarView extends View{
    private final float mDensity;

    public FitStatusBarView(Context context) {
        this(context,null);
    }

    public FitStatusBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FitStatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDensity = getContext().getResources().getDisplayMetrics().density;





     /*   //判断是否有background 属性

        boolean haveBackground = false;
        final TypedArray a = context.obtainStyledAttributes(
                attrs, com.android.internal.R.styleable.View, defStyleAttr, 0);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if(attr == com.android.internal.R.styleable.View_background){
                haveBackground = true;
                break;
            }
        }
*/
       // LLog.Companion.d(TAG,""+());

     /*   Properties properties = new Properties();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewBackgroundHelper,
                defStyleAttr, 0);
        properties.orientation = a.getInt(-601885, 0);
        properties.spanCount = a.getInt(R.styleable.ViewBackgroundHelper, 1);
        properties.reverseLayout = a.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
        properties.stackFromEnd = a.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
        a.recycle();
*/
      /*  TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FitStatusBarView);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N &&typedArray.getBoolean(R.styleable.FitStatusBarView_backgroundEnable, true)){
            setBackgroundColor(0x11000000);
        }
        typedArray.recycle();*/

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M ){
            setBackgroundColor(0x88000000);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int statusBarHeight = getStatusBarHeight(getContext());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT || isInEditMode()){
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), isInEditMode() ? (int) (20 * mDensity) : statusBarHeight);
        }else{
            setMeasuredDimension(0,0);
        }
    }


    /**
     * 获取状态栏的高度
     *
     * @return
     */
    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = (int) context.getResources().getDimension(resourceId);
        }
        return result;
    }
}
