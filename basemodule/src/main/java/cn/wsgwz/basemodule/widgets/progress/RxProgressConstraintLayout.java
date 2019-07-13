package cn.wsgwz.basemodule.widgets.progress;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class RxProgressConstraintLayout extends ConstraintLayout implements RxProgressLayout {



    public RxProgressConstraintLayout(Context context) {
        super(context);
        init();
    }

    public RxProgressConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RxProgressConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RxProgressConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showContent(List<Integer> skipIds) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(List<Integer> skipIds) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showEmpty(OnClickListener buttonClickListener) {

    }

    @Override
    public void showEmpty(OnClickListener buttonClickListener, List<Integer> skipIds) {

    }

    @Override
    public void showEmpty(String description, OnClickListener buttonClickListener, List<Integer> skipIds) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showError(OnClickListener buttonClickListener) {

    }

    @Override
    public void showError(OnClickListener buttonClickListener, List<Integer> skipIds) {

    }

    @Override
    public void showError(String description, OnClickListener buttonClickListener, List<Integer> skipIds) {

    }

    @Override
    public String getCurrentState() {
        return null;
    }

    @Override
    public boolean isContent() {
        return false;
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isError() {
        return false;
    }
}
