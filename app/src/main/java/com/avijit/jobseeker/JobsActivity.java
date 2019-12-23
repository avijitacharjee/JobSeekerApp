package com.avijit.jobseeker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JobsActivity extends AppCompatActivity {

    WebView jobsWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        jobsWebView = findViewById(R.id.jobs_web_view);
        jobsWebView.setWebViewClient(new WebViewClient());
        jobsWebView.getSettings().setJavaScriptEnabled(true);
        jobsWebView.loadUrl(Singleton.getInstance().url);
    }
}
