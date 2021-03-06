package tudorpradan.learnkotlin.flickrbrowser

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "RecyclerItemListen"
class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, private val listener: OnRecycleClickListener) : RecyclerView.SimpleOnItemTouchListener() {

    interface OnRecycleClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)

    }

    private val gestureDetector = GestureDetectorCompat(context, object: GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.d(TAG, ".onSingleTapUp starts")
            val childView = recyclerView.findChildViewUnder(e.x , e.y)
            Log.d(TAG, ".onSingleTapUp calling listener .onItemClick")
            if (childView != null) {
                listener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.d(TAG, ".onLongPress starts")
            val childView = recyclerView.findChildViewUnder(e.x , e.y)
            Log.d(TAG, ".onLongPress calling listener .onItemLongClick")
            if (childView != null) {
                listener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
            }
            super.onLongPress(e)
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        Log.d(TAG, ".onInterceptTouchEvent starts $e")
        val result = gestureDetector.onTouchEvent(e)
        Log.d(TAG,".onInterceptTouchEvent returning $result")
        return result
    }
}