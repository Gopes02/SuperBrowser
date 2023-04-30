package edu.temple.superbrowser

import BookmarkListAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookmarkListActivity : AppCompatActivity() {

    private lateinit var bookmarkManager: BookmarkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark_list)

        bookmarkManager = BookmarkManager(this)

        val bookmarks = bookmarkManager.getAllBookmarks()

        val recyclerView = findViewById<RecyclerView>(R.id.bookmark_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BookmarkListAdapter(bookmarks,
            object : BookmarkListAdapter.OnBookmarkClickListener {
                override fun onBookmarkClick(url: String) {
                    val intent = Intent(this@BookmarkListActivity, BrowserActivity::class.java)
                    intent.putExtra("url", url)
                    startActivity(intent)
                }
            },
            object : BookmarkListAdapter.OnDeleteClickListener {
                override fun onDeleteClick(url: String) {
                    showDeleteConfirmationDialog(url)
                }
            })
    }

    private fun showDeleteConfirmationDialog(url: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_bookmark_title)
            .setMessage(R.string.delete_bookmark_message)
            .setPositiveButton(R.string.delete) { _, _ ->
                bookmarkManager.deleteBookmark(url)
                recreate()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
}
