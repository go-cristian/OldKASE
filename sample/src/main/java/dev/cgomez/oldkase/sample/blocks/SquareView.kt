package dev.cgomez.oldkase.sample.blocks

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

open class SquareView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val width = measuredWidth
    val height = measuredHeight
    val calculatedHeight: Int = width
    if (calculatedHeight != height) {
      setMeasuredDimension(measuredWidth, calculatedHeight)
    }
  }
}