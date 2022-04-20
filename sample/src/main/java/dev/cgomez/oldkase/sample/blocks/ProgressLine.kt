package dev.cgomez.oldkase.sample.blocks

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import dev.cgomez.oldkase.sample.R

class ProgressLine @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

  private val drawable by lazy {
    AnimatedVectorDrawableCompat.create(
      context, R.drawable.progress_line
    )
  }
  private var action = Runnable { repeatAnimation() }

  init {
    background = drawable
    repeatAnimation()
  }

  private fun repeatAnimation() {
    drawable?.start()
    postDelayed(action, 1000)
  }
}