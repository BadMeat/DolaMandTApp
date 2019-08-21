package com.dolan.dolamandtapp.tv


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dolan.dolamandtapp.R
import kotlinx.android.synthetic.main.fragment_tv.*

class TvFragment : Fragment() {

    private lateinit var tvViewModel: TvViewModel
    private lateinit var adapter: TvAdapter
    private val itemTv: MutableList<TvResponse> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TvAdapter(itemTv)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        tvViewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
        tvViewModel.getTv().observe(this, getTv)

        tvViewModel.setTv()
        progress_bar.visibility = View.VISIBLE
    }

    private val getTv = Observer<MutableList<TvResponse>> { t ->
        if (t != null) {
            itemTv.addAll(t)
            progress_bar.visibility = View.INVISIBLE
            adapter.notifyDataSetChanged()
        }
    }
}
