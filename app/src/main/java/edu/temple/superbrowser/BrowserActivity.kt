package edu.temple.superbrowser

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class BrowserActivity : AppCompatActivity(), BrowserControlFragment.BrowserControlInterface, PageViewerFragment.PageViewerInterface, PageListFragment.PageListInterface {


    private lateinit var bookmarkManager: BookmarkManager


    val pager: ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }

    private val browserViewModel : BrowserViewModel by lazy {
        ViewModelProvider(this)[BrowserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = BrowserFragmentStateAdapter(this)
        bookmarkManager = BookmarkManager(this)

        // Move to previous page index
        pager.setCurrentItem(browserViewModel.currentPageIndex, false)

        // Keep track of current page in ViewModel
        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                browserViewModel.currentPageIndex = position

                // Retrieve title from Fragment directly.
                // Exploits FragmentStateAdapter 'f-position' Tag scheme
                supportFragmentManager.findFragmentByTag("f$position")?.run {
                    (this as PageViewerFragment).getPageTitle()?.run {
                        updateTitle(position, this)
                    }
                }
            }
        })

        if (browserViewModel.getCurrentPageCount() == 0) {
            addPage()
        }

    }

    /**
     * Add a new page to the list by
     * - increasing page count
     * - notifying adapter
     * - switching ViewPager to current page
     * - clearing Activity title
     */
    override fun addPage() {
        browserViewModel.addNewPage()
        pager.run {
            adapter?.notifyItemInserted(browserViewModel.getCurrentPageCount())
            setCurrentItem(browserViewModel.getCurrentPageCount(), true)
        }

        updateTitle(browserViewModel.currentPageIndex,"")
    }

    override fun closePage() {
    }


    // Update or clear Activity title
    override fun updateTitle(pageId: Int, title: String) {
        if (pageId == browserViewModel.currentPageIndex) {
            if (title.isNotEmpty())
                supportActionBar?.title = "${getString(R.string.app_name)} - $title"
            else
                supportActionBar?.title = getString(R.string.app_name)
        }
    }

    inner class BrowserFragmentStateAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        // retrieve page count from ViewModel
        override fun getItemCount() : Int {
            return browserViewModel.getCurrentPageCount()
        }

        override fun createFragment(position: Int) = PageViewerFragment.newInstance(position)

    }

    override fun pageSelected(pageIndex: Int) {
        pager.setCurrentItem(pageIndex, true)
    }

    override fun saveBookmark() {
        val currentPage = pager.currentItem
        val pageViewerFragment = supportFragmentManager.findFragmentByTag("f$currentPage") as PageViewerFragment
        val title = pageViewerFragment.webView.title ?: "Untitled"
        val url = pageViewerFragment.webView.url

        if (url == null || url.isEmpty() || url == "about:blank") {
            // Display error message
            Toast.makeText(this, getString(R.string.no_website_error), Toast.LENGTH_SHORT).show()
        } else if (bookmarkManager.isUrlAlreadySaved(url)) {
            // Display error message
            Toast.makeText(this, getString(R.string.url_already_saved_error), Toast.LENGTH_SHORT).show()
        } else {
            val bookmark = Bookmark(title, url)
            bookmarkManager.addBookmark(bookmark)

            Toast.makeText(this, "Bookmark saved", Toast.LENGTH_SHORT).show()
        }
    }
    override fun showBookmarks() {
        val intent = Intent(this, BookmarkListActivity::class.java)
        startActivityForResult(intent, BOOKMARKS_REQUEST_CODE)
    }


    private fun loadUrl(url: String) {
        val pageViewerFragment = supportFragmentManager.findFragmentByTag("page_viewer_fragment") as? PageViewerFragment
        pageViewerFragment?.loadUrl(url)
    }

    companion object {
        const val BOOKMARKS_REQUEST_CODE = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == BOOKMARKS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedURL = data?.getStringExtra("selectedURL")
            selectedURL?.let {
                loadUrlInCurrentFragment(it)
            }
        }
    }

    override fun loadUrlInCurrentFragment(url: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag("f${pager.currentItem}") as PageViewerFragment
        currentFragment.loadUrl(url)
    }
}

