package cn.wsgwz.basemodule.widgets.progress;

import android.view.View;

import java.util.List;

public interface RxProgressLayout {

    void showContent();

    void showContent(List<Integer> skipIds);


    void showLoading();

    void showLoading(List<Integer> skipIds);

    void showEmpty();

    void showEmpty(View.OnClickListener buttonClickListener);

    void showEmpty(View.OnClickListener buttonClickListener, List<Integer> skipIds);

    void showEmpty(String description, View.OnClickListener buttonClickListener, List<Integer> skipIds);

    void showError();

    void showError(View.OnClickListener buttonClickListener);

    void showError(View.OnClickListener buttonClickListener, List<Integer> skipIds);

    void showError(String description, View.OnClickListener buttonClickListener, List<Integer> skipIds);


    String getCurrentState();

    boolean isContent();

    boolean isLoading();

    boolean isEmpty();

    boolean isError();
}
