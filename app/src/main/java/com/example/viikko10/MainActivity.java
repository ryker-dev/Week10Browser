package com.example.viikko10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WebView webview;
    String currentSite, lastSite = "", nextSite = "";
    EditText searchBar;
    ImageButton nextPage, lastPage;
    ArrayList<String> history = new ArrayList<>();
    Button shoutOut, initialize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = findViewById(R.id.webView);
        searchBar = findViewById(R.id.searchBar);
        nextPage = findViewById(R.id.nextPage);
        lastPage = findViewById(R.id.lastPage);

        shoutOut = findViewById(R.id.buttonShoutOut);
        initialize = findViewById(R.id.buttonRevert);

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl("https://google.com/");
        currentSite = "google.com";

        history.add("lut.fi");
        history.add("youtube.com");
        history.add("translate.google.fi");
        history.add("google.com");
        //webview.loadUrl("file:///android_asset/index.html");

    }

    /* Doesn't work properly. Has bugs and problems */
    public void addToHistory () {
        String url = String.valueOf(searchBar.getText());
        int index = history.indexOf(currentSite);

        System.out.println(history.size());
        try {
        /* If current site is not the newest, remove the newer entries */
        if ((index != (history.size() - 1)) && (index >= 0)) {
            for (int i = index; i < 10; i++) {
                history.remove(i);
            }
        };

        /* If 10 items are already in the list, remove the oldest */
        if (history.size() > 9) {
            history.remove(0);
        }
        history.add(url);

        } catch (IndexOutOfBoundsException e) {
            Log.e("addToHistory", "Index out of bounds");
        }
    }

    public void lastPage(View v) {
        if (lastSite.equals("")) {return;}

        nextSite = currentSite;
        loadPage(lastSite);
        lastSite = "";
    };

    public void nextPage(View v) {
        if (nextSite.equals("")) {return;}

        lastSite = currentSite;
        loadPage(nextSite);
        nextSite = "";
    };

    public void refreshPage (String url) {
        webview.loadUrl("http://" + url + "/");
    }

    public void loadPage (String url) {
        webview.loadUrl("http://" + url + "/");
        lastSite = currentSite;
        currentSite = url;
    }

    public void changePage (View v) {
        String url = String.valueOf(searchBar.getText());
        System.out.println("The current searchBar is " + url);

        if (url.equals("index.html")) {
            webview.loadUrl("file:///android_asset/index.html");
            return;
        } else if (url.equals(currentSite)) {
            refreshPage(url);
            return;
        }

        loadPage(url);
    }

    public void shoutOut (View v) {
        webview.evaluateJavascript("javascript:shoutOut()", null);
    }

    public void initialize (View v) {
        webview.evaluateJavascript("javascript:initialize()", null);
    }
}