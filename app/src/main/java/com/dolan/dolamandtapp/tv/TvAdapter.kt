package com.dolan.dolamandtapp.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolan.dolamandtapp.R
import com.squareup.picasso.Picasso

/**
 * Created by Bencoleng on 21/08/2019.
 */
class TvAdapter(
    private val listItem: List<TvResponse>,
    private val listener: (TvResponse) -> Unit
) :
    RecyclerView.Adapter<TvAdapter.TvHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        return TvHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tv,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listItem.size

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        holder.bindItem(listItem[position], listener)
    }

    inner class TvHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgPoster: ImageView = view.findViewById(R.id.img_tv)
        private val txtTitle: TextView = view.findViewById(R.id.txt_title)
        private val txtRating: TextView = view.findViewById(R.id.txt_rating)
        private val txtDesc: TextView = view.findViewById(R.id.txt_desc)

        fun bindItem(e: TvResponse, listener: (TvResponse) -> Unit) {
            Picasso.get().load(e.posterPath).into(imgPoster)
            txtTitle.text = e.name
            txtRating.text = e.rate.toString()
            if (e.desc.isNotEmpty()) {
                txtDesc.text = e.desc
            }
            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}