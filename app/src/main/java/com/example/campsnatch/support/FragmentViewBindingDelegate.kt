package com.example.campsnatch.support

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T: ViewBinding>(
    private val viewBindingFactory: (View) -> T
): ReadOnlyProperty<Fragment, T?> {
    private var binding: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? = binding ?: try {
        createBinding(thisRef)
    } catch (e: IllegalStateException) {
        null
    }

    private fun createBinding(fragment: Fragment): T {
        val lifecycleCallback = object: FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                if (f === fragment) {
                    binding = null
                    try {
                        fragment.parentFragmentManager.unregisterFragmentLifecycleCallbacks(this)
                    } catch (e: Exception) {
                        // Intentional ignore:
                        // The "unregister" is just for cleaning up; if it fails, then there's nothing left to clean up
                    }
                }
            }
        }
        fragment.parentFragmentManager.registerFragmentLifecycleCallbacks(lifecycleCallback, false)

        return viewBindingFactory(fragment.requireView()).also { binding = it }
    }
}

@Suppress("unused")
fun <T: ViewBinding> Fragment.viewBinding(
    viewBindingFactory: (View) -> T
): FragmentViewBindingDelegate<T> = FragmentViewBindingDelegate(viewBindingFactory)