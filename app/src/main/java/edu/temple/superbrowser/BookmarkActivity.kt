package edu.temple.superbrowser

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookmarkActivity : AppCompatActivity() {

    private lateinit var bookmarkManager: BookmarkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        setTitle("Bookmarks")

        bookmarkManager = BookmarkManager(this)

        val bookmarks = bookmarkManager.getAllBookmarks()

        findViewById<ImageButton>(R.id.exitBookmarkButton).setOnClickListener {
            finish()
        }


        val recyclerView = findViewById<RecyclerView>(R.id.bookmark_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BookmarkListAdapter(bookmarks,
            object : BookmarkListAdapter.OnBookmarkClickListener {
                override fun onBookmarkClick(url: String) {
                    val resultIntent = Intent()
                    resultIntent.putExtra("selectedURL", url)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }


            },
            object : BookmarkListAdapter.OnDeleteClickListener {
                override fun onDeleteClick(url: String) {
                    deleteConfirmationDialog(url)
                }
            })
    }

    private fun deleteConfirmationDialog(url: String) {
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
