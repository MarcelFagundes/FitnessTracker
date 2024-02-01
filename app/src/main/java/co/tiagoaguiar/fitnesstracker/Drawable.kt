package co.tiagoaguiar.fitnesstracker

import android.app.AppComponentFactory
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/*
Para chamar a função
val activity = (requireActivity() as AppCompatActivity)
val image = ContextCompat.getDrawable(activity, R.drawable.terminator2)
activity.supportActionBar?.setHomeAsUpIndicator(image?.toCircle(resources, 42))

 */

fun Drawable.toCircle(resourses: Resources, dp: Int): Drawable {
    val b = (this as BitmapDrawable).bitmap
    val aspectRatio = b.width.toDouble() / b.height.toDouble()

    val height: Int
    val width: Int
    if (aspectRatio < 1) {
        width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resourses.displayMetrics
        ).toInt()
        height = (width / aspectRatio).toInt()
    } else {
          height = TypedValue.applyDimension(
              TypedValue.COMPLEX_UNIT_DIP,
              dp.toFloat(),
              resourses.displayMetrics
          ).toInt()
        width = (height * aspectRatio).toInt()
    }

    val newBitmapScaled = Bitmap.createScaledBitmap(b, width, height, false)

    val dimension = if (aspectRatio < 1) width else height
    val output =  Bitmap.createBitmap(
        dimension,
        dimension,
        Bitmap.Config.ARGB_8888
    )

    val print = Paint()
    paint.color = Color.GREEN
    paint.isAntiAlias = true

    val x: Int
    val y : Int

    if (aspectRatio < 1) {
        y = -((newBitmapScaled.height - output.height) / 2)
        x = 0
    } else {
        x = -((newBitmapScaled.width - output.width) / 2)
        y = 0
    }

    val dest = Rect(x, y, newBitmapScaled.width + x,  newBitmapScaled.height + y)

    val r = output.width / 2f
    val canvas = Canvas(output)
    canvas.drawARGB(0,0,0,0)
    canvas.drawCircle(r,r,r,paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(newBitmapScaled, null, dest, paint)

    return BitmapDrawable(resourses,output)
}
