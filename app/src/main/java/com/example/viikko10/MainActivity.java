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
    String currentSite;
    EditText searchBar;
    ImageButton nextPage, lastPage;
    ArrayList<String> history = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = findViewById(R.id.webView);
        searchBar = findViewById(R.id.searchBar);
        nextPage = findViewById(R.id.nextPage);
        lastPage = findViewById(R.id.lastPage);

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

    /*
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                changePage(webview);
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }*/

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

    /* Redundant. Change to better functions */
    public void lastPage(View v) {
        int index = history.indexOf(currentSite);

        try {
            searchBar.setText(history.get(index - 1));
            changePage(webview);
        } catch (IndexOutOfBoundsException e) {
            Log.e("LastPage", "Index out of bounds");
        }
    };

    public void nextPage(View v) {
        int index = history.indexOf(currentSite);

        try {
            searchBar.setText(history.get(index + 1));
            changePage(webview);
        } catch (IndexOutOfBoundsException e) {
            Log.e("NextPage", "Index out of bounds");
        }
    };

    public void changePage (View v) {
        String url = String.valueOf(searchBar.getText());

        if (url == null) {
            System.out.println("EMPTY EMPTY EMPTY EMPTY EMPTY EMPTY");
            url = currentSite;
        } else {
            addToHistory();
            currentSite = url;
        }

        webview.loadUrl("http://" + url + "/");
    }
}