package edu.temple.superbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookmarkListAdapter(private val bookmarks: List<Bookmark>) :
    RecyclerView.Adapter<BookmarkListAdapter.BookmarkViewHolder>() {

    inner class BookmarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.bookmark_title)
        val urlTextView: TextView = view.findViewById(R.id.bookmark_url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark_list_item, parent, false)

        return BookmarkViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.titleTextView.text = bookmark.title
        holder.urlTextView.text = bookmark.url
    }

    override fun getItemCount() = bookmarks.size
}
