package com.iyubinest.oldkase.blocks

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemList @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

  init {
    layoutManager = LinearLayoutManager(context)
    setHasFixedSize(true)
  }

  fun <T> init(
    data: List<T>,
    buildView: (ViewGroup) -> View,
    bindView: (View, T) -> Unit = { _, _ -> }
  ) {
    val gridAdapter = ListAdapter(data, buildView, bindView)
    adapter = gridAdapter
  }

  class ListAdapter<T>(
    private val list: List<T>,
    private val buildView: (ViewGroup) -> View,
    private val bindView: (View, T) -> Unit = { _, _ -> }
  ) : Adapter<Holder>() {

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ): Holder {
      return Holder(buildView(parent))
    }

    override fun onBindViewHolder(
      holder: Holder,
      position: Int
    ) {
      bindView.invoke(holder.itemView, list[position])
    }

    override fun getItemCount() = list.size
  }

  class Holder(itemView: View) : ViewHolder(itemView)
}