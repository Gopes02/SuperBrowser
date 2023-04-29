package edu.temple.superbrowser

import android.os.Bundle
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
        recyclerView.adapter = BookmarkListAdapter(bookmarks)
    }
}
