package fr.centrale.rom.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webview = (WebView) findViewById(R.id.webview);

        Bundle b = getIntent().getExtras();

        String url = "";
        if(b != null)
            url = b.getString("url");

        webview.loadUrl(url);
    }
}
