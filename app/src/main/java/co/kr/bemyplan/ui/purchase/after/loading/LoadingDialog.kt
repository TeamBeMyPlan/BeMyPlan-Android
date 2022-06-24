package co.kr.bemyplan.ui.purchase.after.loading

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import co.kr.bemyplan.R
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.FadingCircle
import timber.log.Timber


class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.dialog_loading)

        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val progressBar = findViewById<View>(R.id.spin_kit) as ProgressBar
        val fadingCircle: Sprite = FadingCircle()
        progressBar.indeterminateDrawable = fadingCircle
    }
}