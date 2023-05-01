package edu.temple.superbrowser

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookmarkListDialogFragment : DialogFragment(), BookmarkListAdapter.OnBookmarkClickListener, BookmarkListAdapter.OnDeleteClickListener {

    private lateinit var onUrlSelectedListener: (String) -> Unit
    private lateinit var bookmarkManager: BookmarkManager
    private lateinit var recyclerView: RecyclerView

    fun setOnUrlSelectedListener(listener: (String) -> Unit) {
        onUrlSelectedListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_bookmark_list_dialog, null)

        bookmarkManager = BookmarkManager(requireContext())
        val bookmarks = bookmarkManager.getAllBookmarks()

        recyclerView = view.findViewById(R.id.bookmark_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = BookmarkListAdapter(bookmarks, this, this)

        return AlertDialog.Builder(requireActivity())
            .setView(view)
            .setTitle(getString(R.string.bookmark_list_title))
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dismiss()
            }
            .create()
    }

    override fun onBookmarkClick(url: String) {
        onUrlSelectedListener(url)
        dismiss()
    }

    override fun onDeleteClick(url: String) {
        showDeleteConfirmationDialog(url) { shouldDelete ->
            if (shouldDelete) {
                bookmarkManager.deleteBookmark(url)
                (recyclerView.adapter as BookmarkListAdapter).removeBookmark(url)
            }
        }
    }

    private fun showDeleteConfirmationDialog(url: String, callback: (Boolean) -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_bookmark_title)
            .setMessage(R.string.delete_bookmark_message)
            .setPositiveButton(R.string.delete) { _, _ ->
                callback(true)
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                callback(false)
            }
            .show()
    }


}
