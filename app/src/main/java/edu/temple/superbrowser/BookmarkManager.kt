package edu.temple.superbrowser

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class BookmarkManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("bookmarks", Context.MODE_PRIVATE)

    fun addBookmark(bookmark: Bookmark) {
        val bookmarks = getAllBookmarks().toMutableList()
        bookmarks.add(bookmark)
        saveBookmarks(bookmarks)
    }

    fun isUrlAlreadySaved(url: String): Boolean {
        return getAllBookmarks().any { it.url == url }
    }

    fun deleteBookmark(url: String) {
        val bookmarks = getAllBookmarks().toMutableList()
        bookmarks.removeAll { it.url == url }
        saveBookmarks(bookmarks)
    }


    fun getAllBookmarks(): List<Bookmark> {
        val bookmarksJson = sharedPreferences.getString("bookmarks", "[]")
        val bookmarksType = object : TypeToken<List<Bookmark>>() {}.type
        return Gson().fromJson(bookmarksJson, bookmarksType)
    }

    private fun saveBookmarks(bookmarks: List<Bookmark>) {
        sharedPreferences.edit().putString("bookmarks", Gson().toJson(bookmarks)).apply()
    }
}
