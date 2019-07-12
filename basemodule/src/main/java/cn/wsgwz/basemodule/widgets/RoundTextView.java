package cn.wsgwz.basemodule.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import cn.wsgwz.basemodule.R;


public class RoundTextView extends AppCompatTextView {

    private int rtvBorderWidth;
    private int rtvBorderColor;
    private float rtvRadius;
    private int rtvBgColor;


    public RoundTextView(Context context) {
        this(context, null);
    }

    public RoundTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundTextView, defStyleAttr, 0);

        if (attributes != null) {

            rtvBorderWidth = attributes.getDimensionPixelSize(R.styleable.RoundTextView_rtvBorderWidth, 0);
            rtvBorderColor = attributes.getColor(R.styleable.RoundTextView_rtvBorderColor, Color.BLACK);
            rtvRadius = attributes.getDimension(R.styleable.RoundTextView_rtvRadius, 0);
            rtvBgColor = attributes.getColor(R.styleable.RoundTextView_rtvBgColor, Color.TRANSPARENT);
            attributes.recycle();
            initBackground();
        }
    }




    private void initBackground() {
        GradientDrawable gradientDrawable = new GradientDrawable();//创建drawable
        gradientDrawable.setColor(rtvBgColor);
        gradientDrawable.setCornerRadius(rtvRadius);
        if (rtvBorderWidth > 0) {
            gradientDrawable.setStroke(rtvBorderWidth, rtvBorderColor);
        }

        Drawable drawable2 = gradientDrawable.getConstantState().newDrawable();
        drawable2.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);


        Drawable drawable3 = gradientDrawable.getConstantState().newDrawable();
        ColorMatrix colorMatrix = new ColorMatrix();
        //0灰
        colorMatrix.setSaturation(0);
        drawable3.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawable2);    // 按下状态 , 设置按下的图片
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, gradientDrawable);     // 默认状态,默认状态下的图片
        stateListDrawable.addState(new int[]{}, drawable3);                                // 不可点击状态


        setBackground(stateListDrawable);
    }


    public int getRtvBorderWidth() {
        return rtvBorderWidth;
    }

    public void setRtvBorderWidth(int rtvBorderWidth) {
        this.rtvBorderWidth = rtvBorderWidth;
        initBackground();
    }

    public int getRtvBorderColor() {
        return rtvBorderColor;
    }

    public void setRtvBorderColor(int rtvBorderColor) {
        this.rtvBorderColor = rtvBorderColor;
        initBackground();
    }

    public float getRtvRadius() {
        return rtvRadius;
    }

    public void setRtvRadius(float rtvRadius) {
        this.rtvRadius = rtvRadius;
        initBackground();
    }

    public int getRtvBgColor() {
        return rtvBgColor;
    }

    public void setRtvBgColor(int rtvBgColor) {
        this.rtvBgColor = rtvBgColor;
        initBackground();
    }
}

