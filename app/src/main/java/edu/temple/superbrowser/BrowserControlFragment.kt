package edu.temple.superbrowser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton


class BrowserControlFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_browser_control, container, false).apply {

            findViewById<ImageButton>(R.id.showBookmarksButton).setOnClickListener {
                val intent = Intent(requireActivity(), BookmarkListActivity::class.java)
                requireActivity().startActivityForResult(intent, BrowserActivity.BOOKMARKS_REQUEST_CODE)
            }
            findViewById<ImageButton>(R.id.shareButton).setOnClickListener { shareCurrentPage() }
            findViewById<ImageButton>(R.id.saveBookmarkButton).setOnClickListener { (requireActivity() as BrowserControlInterface).saveBookmark() }
            findViewById<ImageButton>(R.id.addPageButton).setOnClickListener{(requireActivity() as BrowserControlInterface).addPage()}
            findViewById<ImageButton>(R.id.closePageButton).setOnClickListener{(requireActivity() as BrowserControlInterface).closePage()}
        }
    }


    private fun shareCurrentPage() {
        val activity = requireActivity() as? PageViewerFragment.PageViewerInterface
            ?: return

        val currentPage = (activity as BrowserActivity).pager.currentItem
        val pageViewerFragment = activity.supportFragmentManager.findFragmentByTag("f$currentPage") as PageViewerFragment
        val url = pageViewerFragment.webView.url

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }

        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_chooser_title)))
    }


    interface BrowserControlInterface {
        fun addPage()
        fun closePage()
        fun saveBookmark()
        fun showBookmarks()
        fun loadUrlInCurrentFragment(url: String)

    }

}