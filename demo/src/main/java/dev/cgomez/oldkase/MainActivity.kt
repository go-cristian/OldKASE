package dev.cgomez.oldkase

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

  private val toolbar by lazy { findViewById<MaterialToolbar>(R.id.toolbar) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    replace(InitialFragment())
    toolbar.title = resources.getString(R.string.app_name)
  }
}

@MainThread
internal fun AppCompatActivity.replace(fragment: Fragment) {
  replaceFragment(fragment, R.id.content)
}

@MainThread
internal fun Fragment.replace(fragment: Fragment) {
  replaceFragment(fragment, R.id.content)
}

@MainThread
internal fun AppCompatActivity.add(fragment: Fragment) {
  addFragment(fragment, R.id.content)
}

@MainThread
internal fun Fragment.add(fragment: Fragment) {
  addFragment(fragment, R.id.content)
}
