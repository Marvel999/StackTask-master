package com.Marvel.stackquestion.UI.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.jackandphantom.stackquestion.R;

public class WebViewActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra("URL");
        WebView webView = findViewById(R.id.link_web);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient(WebViewActivity.this));
        webView.loadUrl(url);
        dialog = ProgressDialog.show(WebViewActivity.this, "Loading", "Please wait...", true);


    }

    public class WebViewClient extends android.webkit.WebViewClient
    {
        private boolean loadingFinished = true;
        private boolean redirect = false;


        WebViewClient(Context context) {

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub

            Log.e("MY TAG", "URL in start "+url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub
            if(!redirect){
                loadingFinished = true;
            }

            if(loadingFinished && !redirect){
                //HIDE LOADING IT HAS FINISHED
                if(dialog.isShowing())
                    dialog.dismiss();

        }

    }
}

}
