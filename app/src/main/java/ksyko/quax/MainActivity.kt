package ksyko.quax

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import ksyko.quax.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationManager(this).makeNotification(121)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            run {
                bang(binding)
            }
            true
        }
        binding.ibSearch.setOnClickListener {
            bang(binding)
        }

        binding.btExplore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://duckduckgo.com/bang")
            ContextCompat.startActivity(applicationContext, intent, null)
        }
    }

    private fun bang(binding: ActivityMainBinding) {
        Banger.bang(applicationContext, binding.etSearch.text.toString(), true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miLicenses -> {
                val webView = WebView(this)
                webView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                webView.loadUrl("file:///android_asset/open_source_licenses.html")
                AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
                    .setTitle(getString(R.string.about))
                    .setView(webView)
                    .setPositiveButton(R.string.ok, null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
