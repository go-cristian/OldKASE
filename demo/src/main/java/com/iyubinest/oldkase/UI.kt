@file:Suppress("TopLevelPropertyNaming", "TooManyFunctions")

package com.iyubinest.oldkase

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.util.regex.Pattern

private const val passwordLength = 8
private const val fadeInDuration = 300L
private const val fadeInFrom = 0F
private const val fadeInTo = 1F
const val maxScroll = 5

@MainThread
fun EditText.content() = this.text.toString().trim()

@MainThread
fun EditText.clear() = this.text.clear()

val emailPattern: Pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

@MainThread
fun EditText.isValidEmail(): Boolean = emailPattern.matcher(content()).matches()

@MainThread
fun EditText.hasPasswordLength(): Boolean = content().length >= passwordLength

@MainThread
fun ComponentActivity.to(
  intent: Intent,
  finish: Boolean = false
) {
  if (finish) finish()
  startActivity(intent)
}

@MainThread
fun Fragment.to(
  intent: Intent,
  finish: Boolean = false
) = requireActivity().to(intent, finish)

@MainThread
fun View.hideKeyboard(): Boolean {
  try {
    val inputMethodManager =
      context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
  } catch (ignored: RuntimeException) {
  }
  return false
}

@MainThread
fun AppCompatActivity.contentView(@IdRes id: Int = android.R.id.content): View =
  window.decorView.findViewById<View>(id)

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
fun AppCompatActivity.hasFragments() = supportFragmentManager.backStackEntryCount > 0

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
fun AppCompatActivity.back() {
  supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

@MainThread
fun Fragment.back() {
  (requireActivity() as AppCompatActivity).back()
}

@MainThread
fun AppCompatActivity.showSnack(
  @StringRes message: Int
) {
  Snackbar.make(contentView(), message, LENGTH_LONG).show()
}

@MainThread
fun Fragment.showSnack(
  message: CharSequence
) {
  (requireActivity() as AppCompatActivity).showSnack(message)
}

@MainThread
fun AppCompatActivity.showSnack(
  message: CharSequence
) {
  Snackbar.make(contentView(), message, LENGTH_LONG).show()
}

@MainThread
fun Fragment.showSnack(
  @StringRes message: Int
) {
  (requireActivity() as AppCompatActivity).showSnack(message)
}

@MainThread
fun NavigationView.addOption(
  name: CharSequence,
  @DrawableRes icon: Int? = null,
  action: (() -> Unit)? = null
) {
  val item = menu.add(name)
  icon?.let { item.setIcon(icon) }
  action?.let {
    item.setOnMenuItemClickListener {
      action.invoke()
      true
    }
  }
}

fun View.removeFromParent() {
  this.parent?.let {
    (it as ViewGroup).removeView(this)
  }
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.show(show: Boolean) {
  if (show) {
    show()
  } else {
    conceal()
  }
}

fun View.showOrDisappear(show: Boolean) {
  if (show) {
    show()
  } else {
    disappear()
  }
}

fun View.showOrHide(show: Boolean) {
  if (show) {
    show()
  } else {
    conceal()
  }
}

fun View.show() {
  this.visibility = View.VISIBLE
}

fun View.fadeIn() {
  val currentView = this
  if (!currentView.isVisible()) {
    currentView.animate().alpha(fadeInTo).setDuration(fadeInDuration)
      .setInterpolator(AccelerateInterpolator())
      .setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
          super.onAnimationStart(animation)
          currentView.alpha = fadeInFrom
          visibility = View.VISIBLE
        }
      }).start()
  }
}

fun View.fadeOut() {
  if (this.isVisible()) {
    this.animate().alpha(fadeInFrom).setDuration(fadeInDuration)
      .setInterpolator(AccelerateInterpolator())
      .setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          super.onAnimationEnd(animation)
          visibility = View.GONE
        }
      }).start()
  }
}

fun View.fadeOutAndExecute(f: () -> Unit) {
  if (this.isVisible()) {
    this.animate().alpha(fadeInFrom).setDuration(fadeInDuration)
      .setInterpolator(AccelerateInterpolator())
      .setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          super.onAnimationEnd(animation)
          visibility = View.GONE
          f()
        }
      }).start()
  }
}

fun View.fadeInAndExecute(f: () -> Unit) {
  val currentView = this
  if (!currentView.isVisible()) {
    currentView.animate().alpha(fadeInTo).setDuration(fadeInDuration)
      .setInterpolator(AccelerateInterpolator())
      .setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
          super.onAnimationStart(animation)
          currentView.alpha = fadeInFrom
          visibility = View.VISIBLE
          f()
        }
      }).start()
  }
}

fun View.conceal() {
  this.visibility = View.GONE
}

fun View.disappear() {
  this.visibility = View.INVISIBLE
}

fun TextView.clear() {
  this.text = ""
}

fun RecyclerView.betterSmoothScrollToPosition(targetItem: Int) {
  layoutManager?.apply {
    when (this) {
      is LinearLayoutManager -> {
        val topItem = findFirstVisibleItemPosition()
        val distance = topItem - targetItem
        val anchorItem = when {
          distance > maxScroll -> targetItem + maxScroll
          distance < -maxScroll -> targetItem - maxScroll
          else -> topItem
        }
        if (anchorItem != topItem) scrollToPosition(anchorItem)
        post {
          smoothScrollToPosition(targetItem)
        }
      }
      else -> smoothScrollToPosition(targetItem)
    }
  }
}

fun View.setupItemSizeLinearLayout(
  width: Int = LinearLayout.LayoutParams.MATCH_PARENT,
  height: Int = LinearLayout.LayoutParams.WRAP_CONTENT
) {
  if (layoutParams == null) {
    layoutParams = LinearLayout.LayoutParams(width, height)
  } else {
    layoutParams.width = width
    layoutParams.height = height
  }
}

fun View.applyShadows(
  spotShadow: Int,
  ambientShadow: Int
) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    outlineSpotShadowColor = spotShadow
    outlineAmbientShadowColor = ambientShadow
  }
}

const val REGEX_URL =
  "(http|ftp|https|Http|Https|gbrappi)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?"
const val NEXT_POSITION = 1

fun String.validateUrlPrefix(): String {
  return this.replace("Https:", "https:").replace("Http:", "http:")
}

fun TextView.setSpannableLink(textToSpan: String?) {
  val urlFinder = REGEX_URL.toRegex()
  val url = textToSpan?.let { urlFinder.findAll(it) }
  val spannableString = SpannableString(textToSpan.orEmpty())

  url?.forEach {
    val clickableSpan = object : ClickableSpan() {
      override fun onClick(p0: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.value.validateUrlPrefix()))
        ContextCompat.startActivity(this@setSpannableLink.context, intent, null)
      }
    }
    spannableString.setSpan(
      clickableSpan,
      it.range.first,
      it.range.last + NEXT_POSITION,
      Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
  }
  this.movementMethod = LinkMovementMethod.getInstance()
  this.text = spannableString
}

fun String.hasUrl(): Boolean = URLUtil.isValidUrl(this)

/**
 * This extension prevents multiple clicks over a single [View] by disabling it after handling.
 * [handledListener] returns true if this view was able to handle the click and not other following click is expected.
 */
fun View.setSingleClickListener(handledListener: () -> Boolean) {
  setOnClickListener {
    isEnabled = !handledListener()
  }
}

fun <T, U> List<T>.intersect(
  uList: List<U>,
  filterPredicate: (T, U) -> Boolean
) =
  filter { m -> uList.any { filterPredicate(m, it) } }

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

@MainThread
fun Context.dateFormatter(): DateFormat = android.text.format.DateFormat.getMediumDateFormat(this)
