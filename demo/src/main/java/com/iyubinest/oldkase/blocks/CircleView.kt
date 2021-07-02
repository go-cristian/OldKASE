package com.iyubinest.oldkase.blocks

import android.R.attr
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.Path.Direction.CW
import android.graphics.RectF
import android.util.AttributeSet

class CircleView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : SquareView(context, attrs, defStyleAttr) {

  init {
    val set = intArrayOf(attr.background)
    val attributes = context.obtainStyledAttributes(attrs, set)
    val drawable = attributes.getDrawable(0)
    attributes.recycle()
    if (drawable == null) {
      setBackgroundColor(Color.WHITE)
    }
  }

  override fun draw(canvas: Canvas?) {
    val clipPath = Path()
    val radius = width.toFloat().div(2)
    clipPath.addRoundRect(RectF(canvas!!.clipBounds), radius, radius, CW)
    canvas.clipPath(clipPath)
    super.draw(canvas)
  }
}