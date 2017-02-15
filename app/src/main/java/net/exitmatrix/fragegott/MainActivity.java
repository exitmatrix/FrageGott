package net.exitmatrix.fragegott2;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setRequestedOrientation( getRequestedOrientation() );
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Remove title bar
        webView = (WebView) findViewById(R.id.webView);
        ShowWebPage();
    }

    private void ShowWebPage() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new myWebChromeClient());
        //webView.setWebChromeClient(new VideoEnabledWebChromeClient());

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://rhema.jesus-comes.com/jcrhema.php");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            while (webView.canGoBack())
                webView.goBack();

            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Created by Andreas on 14.02.2017.
     */

    public class myWebChromeClient extends WebChromeClient {
        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout mContentView=null;
        private View mCustomView=null;
        private FrameLayout mCustomViewContainer=null;
        private CustomViewCallback mCustomViewCallback=null;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mContentView = (RelativeLayout) findViewById(R.id.activity_main);
            mContentView.setVisibility(View.GONE);
            mCustomViewContainer = new FrameLayout(MainActivity.this);

            mCustomViewContainer.setLayoutParams(LayoutParameters);
            mCustomViewContainer.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
            setContentView(mCustomViewContainer);
        }
        //          else
//                super.onShowCustomView(view, callback);//throw new RuntimeException("dödööö");


        @Override @SuppressWarnings("deprecation")
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) // Available in API level 14+, deprecated in API level 18+
        {
            onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            if (mCustomView == null) {
                return;
            } else {
                // Hide the custom view.
                mCustomView.setVisibility(View.GONE);
                // Remove the custom view from its container.
                mCustomViewContainer.removeView(mCustomView);
                mCustomView = null;
                mCustomViewContainer.setVisibility(View.GONE);
                mCustomViewCallback.onCustomViewHidden();
                // Show the content view.
                mContentView.setVisibility(View.VISIBLE);
                setContentView(mContentView);
            }
        }
    }


//    public class RotatedVerticalFrameLayout extends FrameLayout{
//
//        public RotatedVerticalFrameLayout(Context context, AttributeSet attrs){
//            super(context, attrs);
//        }
//
//        @SuppressWarnings("SuspiciousNameCombination")
//        @Override
//        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
//            int w = 500;//getWidth();
//            int h = 500;//getHeight();
//
//            setRotation(90.0f);
//            setTranslationX((h - w) / 2);
//            setTranslationY((w - h) / 2);
//
//            ViewGroup.LayoutParams lp = getLayoutParams();
//            lp.height = w;
//            lp.width = h;
//            requestLayout();
//        }
//    }
}
