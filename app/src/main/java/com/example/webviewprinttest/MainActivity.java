package com.example.webviewprinttest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize webview
        myWebView = findViewById(R.id.myWebView);

        //add webview client to handle event of loading
        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                //if page loaded successfully then show print button
                findViewById(R.id.floatingBtn).setVisibility(View.VISIBLE);
            }
        });

        //prepare your html content which will be show in webview
        String htmlDocument = "<html><body>" +
                "<h1>Webview Print Test </h1>" +
                "<h2>I am Webview</h2>" +
                "<p> By PointOfAndroid</p>" +
                "<p> This is some sample content.</p>" +
                "<p> By PointOfAndroid</p>" +
                "<p> This is some sample content.</p>" +
                "<p> By PointOfAndroid</p>" +
                "<p> This is some sample content.</p>" +
                ""+
                "" +
                "" +
                "" + "Put your content here" +
                "" +
                "" +
                "</body></html>";

        //load your html to webview
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://dakku.emirim.kr/totalMain.php");
        myWebView.setWebChromeClient(new WebChromeClient());
        //myWebView.loadData(htmlDocument, "text/HTML", "UTF-8");
    }

    //create a function to create the print job
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        //create object of print manager in your device
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        //create object of print adapter
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        //provide name to your newly generated pdf file
        String jobName = getString(R.string.app_name) + " Print Test";

        //open print dialog
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }

    //perform click pdf creation operation on click of print button click
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printPDF(View view) {
        createWebPrintJob(myWebView);
    }
}

