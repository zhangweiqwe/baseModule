package cn.wsgwz.basemodule.widgets.progress;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import cn.wsgwz.basemodule.R;

import java.util.ArrayList;
import java.util.List;

public class ProgressConstraintLayout extends ConstraintLayout implements ProgressLayout {

    private final String CONTENT = "type_content";
    private final String LOADING = "type_loading";
    private final String EMPTY = "type_empty";
    private final String ERROR = "type_error";

    private LayoutInflater inflater;
    private View view;
    private Drawable defaultBackground;

    private List<View> contentViews = new ArrayList<>();

    private View loadingState;
    private ProgressBar loadingStateProgressBar;

    private View emptyState;
    private ImageView emptyStateImageView;
    private TextView emptyStateContentTextView;
    private Button emptyStateButton;

    private View errorState;
    private ImageView errorStateImageView;
    private TextView errorStateContentTextView;
    private Button errorStateButton;


    private String state = CONTENT;

    public ProgressConstraintLayout(Context context) {
        super(context);
    }

    public ProgressConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressConstraintLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressActivity);
        typedArray.recycle();*/

        defaultBackground = this.getBackground();
    }

    @Override
    public void showContent() {
        switchState(CONTENT, 0, null, null, null, null);
    }

    @Override
    public void showContent(List<Integer> skipIds) {
        switchState(CONTENT, 0, null, null, null, skipIds);
    }


    @Override
    public void showLoading() {
        switchState(LOADING, 0, null, null, null, null);
    }

    @Override
    public void showLoading(List<Integer> skipIds) {
        switchState(LOADING, 0, null, null, null, skipIds);
    }


    @Override
    public void showEmpty(OnClickListener buttonClickListener) {
        showEmpty(0, null, null, buttonClickListener, null);
    }

    @Override
    public void showEmpty(OnClickListener buttonClickListener, List<Integer> skipIds) {
        showEmpty(0, null, null, buttonClickListener, skipIds);
    }

    @Override
    public void showEmpty(String description, OnClickListener buttonClickListener, List<Integer> skipIds) {
        showEmpty(0, description, null, buttonClickListener, skipIds);
    }

    @Override
    public void showEmpty(int icon, String description, OnClickListener buttonClickListener, List<Integer> skipIds) {
        showEmpty(icon, description, null, buttonClickListener, skipIds);
    }

    @Override
    public void showEmpty(int icon, String description, String buttonText, OnClickListener buttonClickListener, List<Integer> skipIds) {
        switchState(EMPTY, icon != 0 ? icon : R.drawable.ic_hourglass_empty_black_24dp, description != null ? description : getContext().getString(R.string.progress_layout_empty_description), buttonText != null ? buttonText : getContext().getString(R.string.progress_layout_empty_button), buttonClickListener, skipIds);
    }

    @Override
    public void showError(OnClickListener buttonClickListener) {
        showError(0, null, null, buttonClickListener, null);
    }

    @Override
    public void showError(OnClickListener buttonClickListener, List<Integer> skipIds) {
        showError(0, null, null, buttonClickListener, skipIds);
    }

    @Override
    public void showError(String description, OnClickListener buttonClickListener, List<Integer> skipIds) {
        showError(0, description, null, buttonClickListener, skipIds);
    }

    @Override
    public void showError(int icon, String description, OnClickListener buttonClickListener, List<Integer> skipIds) {
        showError(icon, description, null, buttonClickListener, skipIds);
    }

    @Override
    public void showError(int icon, String description, String buttonText, View.OnClickListener buttonClickListener, List<Integer> skipIds) {
        switchState(ERROR, icon != 0 ? icon : R.drawable.ic_error_black_24dp, description != null ? description : getContext().getString(R.string.progress_layout_error_description), buttonText != null ? buttonText : getContext().getString(R.string.progress_layout_error_button), buttonClickListener, skipIds);

    }


    private void switchState(String state, int icon, String description,
                             String buttonText, OnClickListener buttonClickListener, List<Integer> skipIds) {
        this.state = state;

        hideAllStates();

        switch (state) {
            case CONTENT:
                setContentVisibility(true, skipIds);
                break;
            case LOADING:
                setContentVisibility(false, skipIds);
                inflateLoadingView();
                break;
            case EMPTY:
                setContentVisibility(false, skipIds);
                inflateEmptyView();

                emptyStateImageView.setImageResource(icon);
                emptyStateContentTextView.setText(description);
                emptyStateButton.setText(buttonText);
                emptyStateButton.setOnClickListener(buttonClickListener);
                break;
            case ERROR:
                setContentVisibility(false, skipIds);
                inflateErrorView();

                errorStateImageView.setImageResource(icon);
                errorStateContentTextView.setText(description);
                errorStateButton.setText(buttonText);
                errorStateButton.setOnClickListener(buttonClickListener);
                break;
        }
    }

    private void switchState(String state, Drawable icon, String title, String description,
                             String buttonText, OnClickListener buttonClickListener, List<Integer> idsOfViewsNotToHide, List<Integer> skipIds) {
        this.state = state;

        hideAllStates();

        switch (state) {
            case CONTENT:
                setContentVisibility(true, skipIds);
                break;
            case LOADING:
                setContentVisibility(false, skipIds);
                inflateLoadingView();
                break;
            case EMPTY:
                setContentVisibility(false, skipIds);
                inflateEmptyView();

                emptyStateImageView.setImageDrawable(icon);
                emptyStateContentTextView.setText(description);
                break;
            case ERROR:
                setContentVisibility(false, skipIds);
                inflateErrorView();

                errorStateImageView.setImageDrawable(icon);
                errorStateContentTextView.setText(description);
                errorStateButton.setText(buttonText);
                errorStateButton.setOnClickListener(buttonClickListener);
                break;
        }
    }

    private void hideAllStates() {
        hideLoadingView();
        hideEmptyView();
        hideErrorView();
        restoreDefaultBackground();
    }

    private void hideLoadingView() {
        if (loadingState != null) {
            loadingState.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (emptyState != null) {
            emptyState.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (errorState != null) {
            errorState.setVisibility(GONE);
        }
    }

    private void restoreDefaultBackground() {
        this.setBackground(defaultBackground);
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (skipIds == null) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            } else {
                if (!skipIds.contains(v.getId())) {
                    v.setVisibility(visible ? View.VISIBLE : View.GONE);
                }
            }
        }
    }

    private void inflateLoadingView() {
        if (loadingState == null) {
            view = inflater.inflate(R.layout.view_loading, null);
            loadingState = view.findViewById(R.id.layout_loading);
            loadingState.setTag(LOADING);

            loadingStateProgressBar = view.findViewById(R.id.progress_bar_loading);
            /*loadingStateProgressBar.getIndeterminateDrawable()
                    .setColorFilter(loadingStateProgressBarColor, PorterDuff.Mode.SRC_IN);*/
            loadingStateProgressBar.requestLayout();


            LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;

            addView(loadingState, layoutParams);
        } else {
            loadingState.setVisibility(VISIBLE);
        }
    }

    private void inflateEmptyView() {
        if (emptyState == null) {
            view = inflater.inflate(R.layout.view_empty, null);
            emptyState = view.findViewById(R.id.layout_empty);
            emptyState.setTag(EMPTY);

            emptyStateImageView = view.findViewById(R.id.image_icon);
            emptyStateContentTextView = view.findViewById(R.id.text_description);
            emptyStateButton = view.findViewById(R.id.button_retry);
            emptyStateImageView.requestLayout();


            LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;

            addView(emptyState, layoutParams);
        } else {
            emptyState.setVisibility(VISIBLE);
        }
    }

    private void inflateErrorView() {
        if (errorState == null) {
            view = inflater.inflate(R.layout.view_error, null);
            errorState = view.findViewById(R.id.layout_error);
            errorState.setTag(ERROR);

            errorStateImageView = view.findViewById(R.id.image_icon);
            errorStateContentTextView = view.findViewById(R.id.text_description);
            errorStateButton = view.findViewById(R.id.button_retry);

            errorStateImageView.requestLayout();


            LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topToTop = ConstraintSet.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
            layoutParams.startToStart = ConstraintSet.PARENT_ID;
            layoutParams.endToEnd = ConstraintSet.PARENT_ID;

            addView(errorState, layoutParams);
        } else {
            errorState.setVisibility(VISIBLE);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null || (!child.getTag().equals(LOADING) &&
                !child.getTag().equals(EMPTY) && !child.getTag().equals(ERROR))) {

            contentViews.add(child);
        }
    }

    @Override
    public String getCurrentState() {
        return state;
    }

    @Override
    public boolean isContentCurrentState() {
        return state.equals(CONTENT);
    }

    @Override
    public boolean isLoadingCurrentState() {
        return state.equals(LOADING);
    }

    @Override
    public boolean isEmptyCurrentState() {
        return state.equals(EMPTY);
    }

    @Override
    public boolean isErrorCurrentState() {
        return state.equals(ERROR);
    }
}
