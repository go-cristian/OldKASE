package dev.cgomez.oldkase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class InitialFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.main, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    val list = view.findViewById<RecyclerView>(R.id.views)
    list.setHasFixedSize(true)
    list.layoutManager = LinearLayoutManager(requireContext())
    list.adapter = ViewsAdapter(allOptions()) {
      add(it)
    }
  }

  class ViewsAdapter(
    private val names: List<Pair<String, Fragment>>,
    private val callback: (Fragment) -> Unit
  ) : Adapter<Holder>() {

    override fun onCreateViewHolder(
      parent: ViewGroup,
      viewType: Int
    ) =
      Holder(LayoutInflater.from(parent.context).inflate(R.layout.main_cell, parent, false))

    override fun onBindViewHolder(
      holder: Holder,
      position: Int
    ) {
      holder.setName(names[position].first)
      holder.callback { callback.invoke(names[position].second) }
    }

    override fun getItemCount() = names.size
  }

  class Holder(itemView: View) : ViewHolder(itemView) {
    private val nameView = itemView.findViewById<TextView>(R.id.view)
    fun setName(name: String) {
      nameView.text = name
    }

    fun callback(function: (position: Int) -> Unit) {
      itemView.setOnClickListener { function.invoke(adapterPosition) }
    }
  }
}