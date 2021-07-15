package dev.cgomez.oldkase

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import dev.cgomez.oldkase.annotations.WidgetComponent
import dev.cgomez.oldkase.blocks.Grid

val colors = listOf(
  "purple",
  "cobalt",
  "blue",
  "green",
  "yellow",
  "orange",
  "red",
  "brown",
  "gray",
  "platinum",
)
val weights = listOf(700, 600, 500, 400, 300, 200, 100, 50)

val allCompound = colors.map { color -> weights.map { "${color}_${it}" } }.flatten()

@WidgetComponent("Typography/Display")
fun typographyDisplay(
  parent: ViewGroup,
  activity: FragmentActivity
): View = parent.viewFrom(R.layout.typography_display)

@WidgetComponent("Typography/Heading")
fun typographyHeading(
  parent: ViewGroup,
  activity: FragmentActivity
): View = parent.viewFrom(R.layout.typography_heading)

@WidgetComponent("Typography/Label")
fun typographyLabel(
  parent: ViewGroup,
  activity: FragmentActivity
): View = parent.viewFrom(R.layout.typography_label)

@WidgetComponent("Typography/Paragraph")
fun typographyParagraph(
  parent: ViewGroup,
  activity: FragmentActivity
): View = parent.viewFrom(R.layout.typography_paragraph)

@WidgetComponent("Buttons")
fun buttons(
  parent: ViewGroup,
  activity: FragmentActivity
): View = parent.viewFrom(R.layout.buttons)

@WidgetComponent("Colors")
fun primitives(
  parent: ViewGroup,
  activity: FragmentActivity
): View {
  val layout = parent.viewFrom(R.layout.grid)
  val grid = layout.findViewById<Grid>(R.id.grid)
  val buildView = { view: ViewGroup -> view.viewFrom(R.layout.icon) }
  val bindView = { view: View, color: String ->
    val title = view.findViewById<TextView>(R.id.title)
    val subtitle = view.findViewById<TextView>(R.id.subtitle)
    view.setBackgroundColor(parent.context.colorFromName(color))
    title.text = color
    subtitle.text = parent.context.colorHexa(color)
    view.setOnClickListener { println(color) }
  }
  grid.init(allCompound, buildView, bindView)
  return grid
}