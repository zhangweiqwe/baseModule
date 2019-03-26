package cn.wsgwz.basemodule.widgets.progress;

import android.view.View;

import java.util.List;

public interface ProgressLayout {

    public void showContent();

    public void showContent(List<Integer> skipIds);


    public void showLoading();

    public void showLoading(List<Integer> skipIds);


    public void showEmpty(View.OnClickListener buttonClickListener);

    public void showEmpty(View.OnClickListener buttonClickListener, List<Integer> skipIds);

    public void showEmpty(String description, View.OnClickListener buttonClickListener, List<Integer> skipIds);

    public void showEmpty(int icon, String description, View.OnClickListener buttonClickListener, List<Integer> skipIds);

    public void showEmpty(int icon, String description, String buttonText, View.OnClickListener buttonClickListener, List<Integer> skipIds);


    public void showError(View.OnClickListener buttonClickListener);
    public void showError(View.OnClickListener buttonClickListener, List<Integer> skipIds);

    public void showError(String description, View.OnClickListener buttonClickListener, List<Integer> skipIds);

    public void showError(int icon, String description, View.OnClickListener buttonClickListener, List<Integer> skipIds);

    public void showError(int icon, String description, String buttonText, View.OnClickListener buttonClickListener, List<Integer> skipIds);


    public String getCurrentState();

    public boolean isContentCurrentState();

    public boolean isLoadingCurrentState();

    public boolean isEmptyCurrentState();

    public boolean isErrorCurrentState();
}
