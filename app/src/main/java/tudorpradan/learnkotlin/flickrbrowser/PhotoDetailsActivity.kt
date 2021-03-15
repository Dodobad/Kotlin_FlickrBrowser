package tudorpradan.learnkotlin.flickrbrowser

import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_details.*

private const val TAG = "PhotoDetailsAct"
class PhotoDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        activateToolbar(true)
        Log.d(TAG, ".onCreate ends")

        val photo = intent.getParcelableExtra(PHOTO_TRANSFER) as Photo
        photo_title.text = resources.getString(R.string.photo_title_text, photo.title)
        photo_tags.text = resources.getString(R.string.photo_tags_text, photo.tags)
        photo_author.text = photo.author

        Picasso.get().load(photo.photoUrl)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(photo_image)


    }
}