package com.example.forcezheng.loadingwebview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.forcezheng.loadingwebview.R;

/**
 * 自定义带进度条的WebView
 *
 * @author zhengwang
 * @email zhengwang043@foxmail.com
 * @date 2016/10/8
 */
public class LoadingWebView extends WebView {
    private ProgressBar progressBar;

    public LoadingWebView(Context context) {
        super(context);
        addProgressBar(context);
    }

    public LoadingWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addProgressBar(context);
    }

    /**
     * 初始化进度条
     */
    private void addProgressBar(Context context) {
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5, 0, 0));
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg));
        addView(progressBar);

        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(GONE);
            } else {
                if (progressBar.getVisibility() == GONE) {
                    progressBar.setVisibility(VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
    }


}
