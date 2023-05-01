package edu.temple.superbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class BookmarkListAdapter(
    private val bookmarks: List<Bookmark>,
    private val onBookmarkClickListener: OnBookmarkClickListener,
    private val onDeleteClickListener: OnDeleteClickListener?
) : RecyclerView.Adapter<BookmarkListAdapter.BookmarkViewHolder>() {

    interface OnBookmarkClickListener {
        fun onBookmarkClick(url: String)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(url: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark_list_item, parent, false)
        return BookmarkViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarks[position]
        holder.titleTextView.text = bookmark.title

        holder.itemView.setOnClickListener { onBookmarkClickListener.onBookmarkClick(bookmark.url) }
        holder.deleteButton.setOnClickListener { onDeleteClickListener?.onDeleteClick(bookmark.url) }
    }

    override fun getItemCount() = bookmarks.size

    inner class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.bookmark_title)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    fun removeBookmark(url: String) {
        val index = bookmarks.indexOfFirst { it.url == url }
        if (index != -1) {
            (bookmarks as MutableList).removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
