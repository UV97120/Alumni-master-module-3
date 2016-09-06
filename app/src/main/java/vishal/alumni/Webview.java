package vishal.alumni;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Webview extends AppCompatActivity {

    private ProgressBar pb;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        pb = (ProgressBar) findViewById(R.id.progressBar);

        String url = getIntent().getStringExtra("URL");
        WebView wv = (WebView) findViewById(R.id.wv);
        wv.loadUrl(url);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(s));
                startActivity(i);
            }
        });
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

        });
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress < 100 && pb.getVisibility() == View.GONE) {
                    pb.setVisibility(View.VISIBLE);
                }
                if(newProgress == 100 && pb.getVisibility() == View.VISIBLE){
                    pb.setVisibility(View.GONE);
                }
            }
        });
    }
}
