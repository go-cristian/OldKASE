@file:Suppress("TopLevelPropertyNaming", "TooManyFunctions")

package com.iyubinest.oldkase

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

@MainThread
fun EditText.content() = this.text.toString().trim()

@MainThread
fun EditText.clear() = this.text.clear()

fun FragmentTransaction.navAnimations() = setCustomAnimations(
  R.anim.enter_from_right,
  R.anim.exit_to_left,
  R.anim.enter_from_left,
  R.anim.exit_to_right,
)

@MainThread fun AppCompatActivity.replaceFragment(
  fragment: Fragment,
  @IdRes id: Int = android.R.id.content
) {
  supportFragmentManager
    .beginTransaction()
    .navAnimations()
    .replace(id, fragment, fragment::class.java.name)
    .commit()
}

@MainThread
fun Fragment.replaceFragment(
  fragment: Fragment,
  @IdRes id: Int = android.R.id.content
) {
  (requireActivity() as AppCompatActivity).replaceFragment(fragment, id)
}

@MainThread
fun AppCompatActivity.addFragment(
  fragment: Fragment,
  @IdRes id: Int = android.R.id.content
) {
  supportFragmentManager
    .beginTransaction()
    .navAnimations()
    .add(id, fragment, fragment::class.java.name)
    .addToBackStack(null)
    .commit()
}

@MainThread
fun Fragment.addFragment(
  fragment: Fragment,
  @IdRes id: Int = android.R.id.content
) {
  (requireActivity() as AppCompatActivity).addFragment(fragment, id)
}

@MainThread
fun ViewGroup.viewFrom(@LayoutRes res: Int) =
  LayoutInflater.from(context).inflate(res, this, false)

@MainThread
@ColorInt
fun Context.colorFromName(name: String): Int {
  val id = resources.getIdentifier(name, "color", packageName)
  return ContextCompat.getColor(this, id)
}

@MainThread
fun Context.colorHexa(name: String): String {
  val id = resources.getIdentifier(name, "color", packageName)
  return resources.getString(id)
}
