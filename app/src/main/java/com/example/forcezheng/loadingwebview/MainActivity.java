package com.example.forcezheng.loadingwebview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.forcezheng.loadingwebview.view.LoadingWebView;

public class MainActivity extends Activity {
    private LoadingWebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        wb = (LoadingWebView) findViewById(R.id.wb);
        initWebView();
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        wb.setWebViewClient(new WebViewClient());
        WebSettings settings = wb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        wb.loadUrl("file:///android_asset/webview.html");
        wb.addJavascriptInterface(new JsInteration(), "control");
    }

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            Android2JsNoParmNoResult(wb);
//            Android2JsHaveParmNoResult(wb);
//            wb.loadUrl("javascript:toBaiDu");
            Android2JsHaveParmHaveResult2(wb);
        }
    }

    /**
     * js调用android的方法
     */
    class JsInteration {
        @JavascriptInterface
        public void toastMessage(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void onSumResult(int result) {
            Toast.makeText(getApplicationContext(), "我是android调用js方法(4.4前)，入参是1和2，js返回结果是" + result, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * android调用js无参无返回值函数
     *
     * @param view
     */
    public void Android2JsNoParmNoResult(View view) {
        final String call = "javascript:sayHello()";
        wb.post(new Runnable() {
            @Override
            public void run() {
                wb.loadUrl(call);
            }
        });

    }

    /**
     * android调用js有参无返回值函数
     *
     * @param view
     */
    public void Android2JsHaveParmNoResult(View view) {
        final String call = "javascript:alertMessage(" + "我是android传过来的内容,hey man" + ")";
        wb.post(new Runnable() {
            @Override
            public void run() {
                wb.loadUrl(call);
            }
        });
    }

    /**
     * android调用js有参有返回值函数（4.4之前）
     *
     * @param view
     */
    public void Android2JsHaveParmHaveResult(View view) {
        final String call = "javascript:sumToJava(1,2)";
        wb.post(new Runnable() {
            @Override
            public void run() {
                wb.loadUrl(call);
            }
        });
    }

    /**
     * android调用js有参有返回值函数（4.4之后）
     * evaluateJavascript方法必须在UI线程（主线程）调用，因此onReceiveValue也执行在主线程
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void Android2JsHaveParmHaveResult2(View view) {
        wb.evaluateJavascript("sumToJava2(3,4)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String Str) {
                Toast.makeText(getApplicationContext(), "我是android调用js方法(4.4后)，入参是3和4，js返回结果是" + Str, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        wb.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stop的时候设置false防止挂在后台，js耗电
        wb.getSettings().setJavaScriptEnabled(false);
    }
}
