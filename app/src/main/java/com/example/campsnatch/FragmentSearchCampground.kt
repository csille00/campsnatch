package com.example.campsnatch

import ServerProxy
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.campsnatch.databinding.FragSearchCampgroundBinding
import com.example.campsnatch.model.Campground
import com.example.campsnatch.support.viewBinding

class FragmentSearchCampground : Fragment() {
    private val binding: FragSearchCampgroundBinding? by viewBinding(FragSearchCampgroundBinding::bind)
    private var cont: Context? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CampgroundsAdapter
    private var campgrounds: List<Campground> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cont = activity?.applicationContext
        return FragSearchCampgroundBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding!!.recyclerView

        val serverProxy = ServerProxy()
        val adapter: CampgroundsAdapter
        serverProxy.getCampgrounds { response ->
        }

    }

    inner class CampgroundsAdapter(private val campgrounds: List<Campground>) : RecyclerView.Adapter<CampgroundsAdapter.CampgroundViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampgroundViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_campground, parent, false)
            return CampgroundViewHolder(view)
        }

        override fun onBindViewHolder(holder: CampgroundViewHolder, position: Int) {
            val campground = campgrounds[position]
            holder.bind(campground)
        }

        override fun getItemCount(): Int = campgrounds.size

        inner class CampgroundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvCampgroundName: TextView = itemView.findViewById(R.id.tvCampgroundName)

            fun bind(campground: Campground) {
                tvCampgroundName.text = campground.campgroundName
                // Bind other views here
            }
        }
    }
}