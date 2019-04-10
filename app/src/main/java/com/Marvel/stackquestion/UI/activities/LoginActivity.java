package com.Marvel.stackquestion.UI.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jackandphantom.stackquestion.R;
import com.Marvel.stackquestion.Utils.SharedPreferenceUtil;

import java.net.URI;
import java.net.URISyntaxException;

public class LoginActivity extends AppCompatActivity {

    private Button textView;
    private  WebView webView;
    private ProgressDialog dialog;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferenceUtil = new SharedPreferenceUtil(LoginActivity.this);
        if (sharedPreferenceUtil.getFirstTimeLogin()) {
            Intent intent = new Intent(LoginActivity.this, TagSelectionActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.login);
        relativeLayout = findViewById(R.id.layout);
        webView = findViewById(R.id.web);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(LoginActivity.this, "Loading", "Please wait...", true);

                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVisibility(View.VISIBLE);
                webView.setWebViewClient(new WebViewClient(LoginActivity.this));
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl("https://stackoverflow.com/oauth/dialog?client_id=14798" +
                        "&redirect_uri=https://com.jackandphantom.stackquestion&scope=no_expiry");

            }
        });
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

            loadingFinished = false;
            try {
                String hash = new URI(url).getFragment();
                int index = hash.indexOf("=");
                String sub = hash.substring(index+1);
                sharedPreferenceUtil.setAcessToken(sub);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            if (!loadingFinished) {
                redirect = true;
            }

            loadingFinished = false;
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
                relativeLayout.setVisibility(View.GONE);

            } else{
                redirect = false;
            }

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Intent intent = new Intent(LoginActivity.this, TagSelectionActivity.class);
            sharedPreferenceUtil.setFirstTimeLogin(true);
            view.setVisibility(View.GONE);
            startActivity(intent);
            finish();
        }
    }
}
