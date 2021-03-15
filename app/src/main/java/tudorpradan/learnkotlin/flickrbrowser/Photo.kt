package tudorpradan.learnkotlin.flickrbrowser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photo(var title: String, var author: String, var authorId: String, var tags: String, var image: String, var photoUrl: String) : Parcelable
//
//    companion object {
//        private const val serialVersionUID = 1L
//    }
//
//    override fun toString(): String {
//        return "Photo(title='$title', author='$author', authorId='$authorId', tags='$tags', image='$image', photoUrl='$photoUrl')"
//    }
//
//    @Throws(IOException::class)
//    private fun writeObject(out: java.io.ObjectOutputStream) {
//        Log.d("Photo", "writeObject called")
//        out.writeUTF(title)
//        out.writeUTF(author)
//        out.writeUTF(authorId)
//        out.writeUTF(photoUrl)
//        out.writeUTF(tags)
//        out.writeUTF(image)
//    }
//
//    @Throws(IOException::class, ClassNotFoundException::class)
//    private fun readObject(inStream: java.io.ObjectInputStream) {
//        Log.d("Photo", "readObject called")
//        title = inStream.readUTF()
//        author = inStream.readUTF()
//        authorId = inStream.readUTF()
//        photoUrl = inStream.readUTF()
//        tags = inStream.readUTF()
//        image = inStream.readUTF()
//    }
//
//    @Throws(ObjectStreamException::class)
//    private fun readObjectNoData() {
//        Log.d("Photo", "readObjectNoData called")
//    }
//}