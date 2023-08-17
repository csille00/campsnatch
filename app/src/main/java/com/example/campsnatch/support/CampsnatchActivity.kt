package com.example.campsnatch.support

import android.R
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.contains
import androidx.fragment.app.*
import androidx.viewbinding.ViewBinding
import com.google.android.material.card.MaterialCardView

open class CampsnatchActivity: AppCompatActivity() {

    private val contentView by lazy {
        findViewById(android.R.id.content) as? ContentFrameLayout
    }

    protected inline fun <T : ViewBinding> viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }

    override fun startActivity(intent: Intent) {
        // if the intent is implicit and they have no app that can handle the implicit intent
        if (intent.component == null && intent.resolveActivity(packageManager) == null) {
            // tell android to show a dialog that says they have no app that can handle the intent
            super.startActivity(Intent.createChooser(intent, null))
        } else {
            super.startActivity(intent)
        }
    }

    fun replaceFragment(fragment: Fragment, fragManagerId: Int){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(fragManagerId, fragment)
        transaction.addToBackStack(null) // This line adds the transaction to the back stack
        transaction.commit()
    }



    private fun Int.toDp() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()

    private val progressBar by lazy {
        ProgressBar(this, null, R.attr.progressBarStyle).apply {
            id = View.generateViewId()
            isIndeterminate = true
        }
    }
    private val progressBarTextView by lazy {
        TextView(this).apply {
            id = View.generateViewId()
        }
    }
    private val progressBarCardView by lazy {
        MaterialCardView(this).apply {
            id = View.generateViewId()
            radius = 8.toDp().toFloat()
        }
    }
    private val progressBarLayout by lazy {
        ConstraintLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT).apply { gravity = Gravity.CENTER }
        }
    }
    private val progressCardLayout by lazy {
        ConstraintLayout(this).apply {
            layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                marginStart = 8.toDp()
                marginEnd = 8.toDp()
                topMargin = 8.toDp()
                bottomMargin = 8.toDp()
            }
        }
    }

    @JvmOverloads
    fun showProgressBar(message: String? = null, cancelable: Boolean = true) {
        fun ConstraintSet.connectViewAndCenterInParent(v: View) {
            connect(v.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(v.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(v.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(v.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        }
        contentView?.let {
            //In case the dismiss didn't get called, check to see if view is still added to the root and remove it before adding if so
            if (it.contains(progressBarLayout)) it.removeView(progressBarLayout.apply { removeAllViews() })
            progressCardLayout.removeAllViews()
            progressBarCardView.removeAllViews()

            // Add progress bar and textview to constraintlayout
            ConstraintSet().apply {
                progressCardLayout.addView(progressBar)
                message?.let { progressCardLayout.addView(progressBarTextView.apply { text = it }) }
                clone(progressCardLayout)

                connectViewAndCenterInParent(progressBar)
                connect(progressBarTextView.id, ConstraintSet.TOP, progressBar.id, ConstraintSet.BOTTOM, 8)
                connect(progressBarTextView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                connect(progressBarTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                connect(progressBarTextView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

                applyTo(progressCardLayout)
            }

            // Add constraintlayout to the CardView
            progressBarCardView.addView(progressCardLayout)

            it.addView(progressBarLayout)
            // Add cardview to overall layout
            ConstraintSet().apply {
                progressBarLayout.addView(progressBarCardView)
                clone(progressBarLayout)
                connectViewAndCenterInParent(progressBarCardView)

                applyTo(progressBarLayout)
            }


            if (!cancelable) {
                progressBarLayout.setBackgroundColor(resources.getColor(R.color.black))
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
    }
    fun dismissProgressBar() {
        contentView?.removeView(progressBarLayout.apply { removeAllViews() })
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}