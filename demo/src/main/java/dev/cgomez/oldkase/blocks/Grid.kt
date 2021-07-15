package dev.cgomez.oldkase.blocks

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Grid @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

  init {
    layoutManager = GridLayoutManager(context, 3)
    setHasFixedSize(true)
  }

  fun <T> init(
    data: List<T>,
    buildView: (ViewGroup) -> View,
    bindView: (View, T) -> Unit
  ) {
    val gridAdapter = GridAdapter(data, buildView, bindView)
    adapter = gridAdapter
  }

  class GridAdapter<T>(
    private val list: List<T>,
    private val buildView: (ViewGroup) -> View,
    private val bindView: (View, T) -> Unit = { _, _ -> }
  ) : Adapter<GridHolder>() {

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): GridHolder {
      return GridHolder(buildView(parent))
    }

    override fun onBindViewHolder(
      holder: GridHolder,
      position: Int
    ) {
      bindView.invoke(holder.itemView, list[position])
    }

    override fun getItemCount() = list.size
  }

  class GridHolder(itemView: View) : ViewHolder(itemView)
}