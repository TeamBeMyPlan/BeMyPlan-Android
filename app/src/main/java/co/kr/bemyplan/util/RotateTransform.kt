package co.kr.bemyplan.util
import android.graphics.Bitmap
import android.graphics.Matrix
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class RotateTransform(rotateRotationAngle: Float) : BitmapTransformation() {
    private var rotateRotationAngle = 0f
    init {
        this.rotateRotationAngle = rotateRotationAngle
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(("rotate$rotateRotationAngle").toByte())
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotateRotationAngle)
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.width, toTransform.height, matrix, true)
    }
}