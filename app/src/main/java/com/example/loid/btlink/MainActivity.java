package com.example.loid.btlink;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) this.findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new BasicJsApplication(this), "androidinterface");
        webView.loadUrl("http://149.28.75.108/MUIStudy/search.html");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
    }

    public class BasicJsApplication {
        private Context context;

        public BasicJsApplication(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public boolean ClipBoard(String clipString) {
            clipString = "magnet:?xt=urn:btih:"+clipString;
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData;
            clipData = ClipData.newPlainText("text", clipString);
            clipboardManager.setPrimaryClip(clipData);
            return true;
        }

        @JavascriptInterface
        public String getResultList(String searchName) {
            int i = 0;
            String text = "";

            httpRequest request = new httpRequest();
            request.get("https://www.btcherries.com/search/"+searchName+"-1.html");

            //System.out.println(request.text());
            Pattern pattern = Pattern.compile("<a href=\"/magnet/(.*?).html\" target=\"_blank\">(.*?)</a>");
            Matcher matcher = pattern.matcher(request.text());

            while(matcher.find()) {
                text += "{";
                text += "\"link\":" + "\"" + matcher.group(1) + "\",";
                text += "\"name\":" + "\"" + matcher.group(2).replaceAll("\"", "'") + "\"";
                text += "},";
                i++;
            }

            text = text.replaceAll("\\[", "|");
            text = "[" + text.substring(0, text.length()-1) + "]";

            System.out.println(text);
            return text;
        }

    }
}
