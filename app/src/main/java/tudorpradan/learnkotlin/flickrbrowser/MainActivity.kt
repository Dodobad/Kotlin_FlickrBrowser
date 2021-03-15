package tudorpradan.learnkotlin.flickrbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : BaseActivity(), GetRawData.OnDownloadComplete,
    GetFlickrJSONData.OnDataAvailable, RecyclerItemClickListener.OnRecycleClickListener {

    private val flickrRecycleViewAdaptor = FlickrRecycleViewAdaptor(ArrayList())


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activateToolbar(false)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,recycler_view,this))
        recycler_view.adapter = flickrRecycleViewAdaptor

//        val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne", "android,oreo", "en-us", true)
//        val getRawData = GetRawData(this)
       // getRawData.setDownloadCompleteListener(this)
//        getRawData.execute(url)

        Log.d(TAG, "onCreate ends")
    }

    override fun onItemClick(view: View, position: Int) {
        Log.d(TAG,".onItemClick starts")
        //Toast.makeText(this,"Normal tap at position $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        Log.d(TAG, ".onItemLongClick starts")
        //Toast.makeText(this,"Long tap at position $position", Toast.LENGTH_SHORT).show()
        val photo = flickrRecycleViewAdaptor.getPhoto(position)
        if(photo != null) {
            val intent = Intent(this, PhotoDetailsActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER, photo)
            startActivity(intent)
        }
    }

    private fun createUri(baseUrl: String, searchCriteria: String, lang: String, matchAll: Boolean) : String {
        Log.d(TAG, ".createUri starts")
        return Uri.parse(baseUrl).
        buildUpon().
        appendQueryParameter("tags", searchCriteria).
        appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY").
        appendQueryParameter("lang", lang).appendQueryParameter("format", "json").
        appendQueryParameter("nojsoncallback", "1").
        build().
        toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemsSelected called")
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDownloadComplete called")

            val getFlickrJSONData = GetFlickrJSONData(this)
            getFlickrJSONData.execute(data)
        } else {
            Log.d(TAG, "onDownloadComplete failed with status $status. Error message is: $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG,"onDataAvailable called")
        flickrRecycleViewAdaptor.loadNewData(data)
        Log.d(TAG,"onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.e(TAG, "OnError called, error ${exception.message}")
    }

    override fun onResume() {
        Log.d(TAG,".onResume starts")
        super.onResume()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult = sharedPref.getString(FLICKR_QUERY, "")


            if(!queryResult.isNullOrBlank()) {
                val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne", queryResult, "en-us", true)
                val getRawData = GetRawData(this)
                getRawData.execute(url)
            }

        Log.d(TAG,".onResume ends")
    }
}