package com.example.campsnatch

import ServerProxy
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.campsnatch.databinding.FragSearchCampgroundBinding
import com.example.campsnatch.model.Campground
import com.example.campsnatch.support.viewBinding

class FragmentSearchCampground : Fragment() {
    private val binding: FragSearchCampgroundBinding? by viewBinding(FragSearchCampgroundBinding::bind)
    private var cont: Context? = null

    private lateinit var adapter: CampgroundsAdapter
    private var campgrounds: MutableList<Campground> = mutableListOf()

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

        // Initialize the adapter with an empty list
        adapter = CampgroundsAdapter(campgrounds)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context) // Set the LayoutManager
        binding?.recyclerView?.adapter = adapter

        val serverProxy = ServerProxy()

        serverProxy.getCampgrounds { response ->
            campgrounds =
                response?.campgrounds?.sortedBy { it.campgroundName } as MutableList<Campground> // Update the outer campgrounds variable
            adapter.submitList(campgrounds) // Submit the list to the adapter

            binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false // You can handle the submit action here if needed
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterCampgrounds(newText.orEmpty())
                    return true
                }
            })
        }
    }



    private fun filterCampgrounds(query: String) {
        val filteredList = campgrounds.filter {
            it.campgroundName.contains(query, ignoreCase = true)
        }
        adapter.submitList(filteredList)
    }

    inner class CampgroundsAdapter(private val campgrounds: MutableList<Campground>) : RecyclerView.Adapter<CampgroundsAdapter.CampgroundViewHolder>() {

        fun submitList(newCampgrounds: List<Campground>) {
            campgrounds.clear()
            campgrounds.addAll(newCampgrounds)
            notifyDataSetChanged() // Notify the RecyclerView that the data has changed
        }

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
                tvCampgroundName.setOnClickListener{
                    val intent = Intent(activity, BookingActivity::class.java)
                    startActivity(intent)                }
                // Bind other views here
            }
        }
    }
}