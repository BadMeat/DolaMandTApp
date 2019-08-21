package com.dolan.dolamandtapp.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolan.dolamandtapp.R
import com.squareup.picasso.Picasso

class MovieAdapter(private val itemList: List<MovieResponse>) : RecyclerView.Adapter<MovieAdapter.MovieAdapter>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter {
        return MovieAdapter(LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false))
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: MovieAdapter, position: Int) {
        holder.bindItem(itemList[position])
    }

    class MovieAdapter(view: View) : RecyclerView.ViewHolder(view) {
        private val imgPoster: ImageView = view.findViewById(R.id.img_tv)
        private val txtTitle: TextView = view.findViewById(R.id.txt_title)
        private val txtDesc: TextView = view.findViewById(R.id.txt_desc)
        private val txtRate: TextView = view.findViewById(R.id.txt_rating)

        fun bindItem(e: MovieResponse) {
            Picasso.get().load(e.poster).into(imgPoster)
            txtTitle.text = e.title
            txtRate.text = e.rate.toString()
            txtDesc.text = e.desc
        }
    }
}