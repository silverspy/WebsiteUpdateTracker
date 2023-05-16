package fr.silverspy.wut

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WebsiteListAdapter(private val websites: List<Website>) :
    RecyclerView.Adapter<WebsiteListAdapter.WebsiteViewHolder>() {

    inner class WebsiteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val websiteName: TextView = itemView.findViewById(R.id.website_name)
        val websiteUrl: TextView = itemView.findViewById(R.id.website_url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.website_list_item, parent, false)
        return WebsiteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val currentWebsite = websites[position]
        holder.websiteName.text = currentWebsite.name
        holder.websiteUrl.text = currentWebsite.url
    }

    override fun getItemCount(): Int {
        return websites.size
    }
}
