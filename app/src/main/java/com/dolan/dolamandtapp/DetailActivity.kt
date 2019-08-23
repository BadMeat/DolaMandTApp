package com.dolan.dolamandtapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var data: DetailResponse

    companion object {
        const val EXTRA_ID = "extraId"
        const val EXTRA_TYPE = "extraType"
    }

    private val getResponse = Observer<DetailResponse> {
        if (it != null) {
            data = it
            txt_title.text = data.title
            txt_rate.text = data.rate.toString()
            txt_detail.text = data.desc
            Picasso.get().load(data.poster).into(img_poster)
            rate_score.rating = data.rate.toFloat() / 2f
            progress_bar.visibility = View.INVISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val id = intent.getIntExtra(EXTRA_ID, 0)
        val type = intent.getIntExtra(EXTRA_TYPE, 0)

        Log.d("Extra Type", type.toString())

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.getData().observe(this, getResponse)
        viewModel.setData(id, type)

        rate_score.isClickable = false
        rate_score.isLongClickable = false
        rate_score.isClearRatingEnabled = false

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
