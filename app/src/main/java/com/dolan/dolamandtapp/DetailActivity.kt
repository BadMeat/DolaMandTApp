package com.dolan.dolamandtapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extraTitle"
        const val EXTRA_RATE = "extraRate"
        const val EXTRA_DETAIL = "extraDetail"
        const val EXTRA_IMAGE = "extraImage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        txt_title.text = intent.getStringExtra(EXTRA_TITLE)
        txt_rate.text = intent.getDoubleExtra(EXTRA_RATE, 0.0).toString()
        txt_detail.text = intent.getStringExtra(EXTRA_DETAIL)
        Picasso.get().load(intent.getStringExtra(EXTRA_IMAGE)).into(img_poster)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
