package com.dolan.dolamandtapp.movie


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dolan.dolamandtapp.DetailActivity
import com.dolan.dolamandtapp.R
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var movieModel: MovieViewModel

    private val movieList: MutableList<MovieResponse> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter(movieList) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, it.id)
            intent.putExtra(DetailActivity.EXTRA_TYPE, 1)
            startActivity(intent)
        }

        movieModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieModel.getItem().observe(this, getMovie)

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        movieModel.setMovie(resources.getString(R.string.no_desc))

        progress_bar.visibility = View.VISIBLE
    }

    private val getMovie = Observer<MutableList<MovieResponse>> {
        if (it != null) {
            movieList.addAll(it)
            adapter.notifyDataSetChanged()
            progress_bar.visibility = View.INVISIBLE
        }
    }
}
