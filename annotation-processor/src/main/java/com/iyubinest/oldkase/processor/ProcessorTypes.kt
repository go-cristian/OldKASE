package com.iyubinest.oldkase.processor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName

class ProcessorTypes {
  companion object {
    val string = String::class.asClassName()
    val list = ClassName("kotlin.collections", "List")
    val FRAGMENT = ClassName("androidx.fragment.app", "Fragment")
    val pair = ClassName("kotlin", "Pair").parameterizedBy(string, FRAGMENT)
    val LAYOUT_INFLATER = ClassName("android.view", "LayoutInflater")
    val optionsReturn = list.parameterizedBy(pair)
    val VIEW_GROUP = ClassName("android.view", "ViewGroup").copy(true)
    val BUNDLE = ClassName("android.os", "Bundle").copy(true)
    val VIEW = ClassName("android.view", "View").copy(true)
  }
}