package com.vendur.newsreader

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vendur.newsreader.api.BASE_URL
import retrofit2.http.Url
import java.lang.Exception

class InNews : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_news)

        val intent = intent
        val url = intent.getStringExtra("url")
        if (url != null) {
            createWebView(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun createWebView(url : String){
        val webView : WebView = findViewById(R.id.webView)
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_news,menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val intent = intent
        val mSource = intent.getStringExtra("source")
        val url = intent.getStringExtra("url")
        if (url != null) {
            createWebView(url)
        }

       if (id == R.id.view_web){
           val i = Intent(Intent.ACTION_VIEW)
           i.data = Uri.parse(url)
           startActivity(i)
           return true
       }

       else if (id == R.id.share){
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plan"
                i.putExtra(Intent.EXTRA_SUBJECT, mSource)
                val body = "$url\nОтправлено из News Reader\n"
                i.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(i, "Поделиться через:"))

            }catch (e: Exception){
                Toast.makeText(this,"Извините, невозможно поделиться ссылкой.", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}