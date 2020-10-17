package com.qiyei.ui.ui.fragment

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import java.lang.IllegalArgumentException


fun Activity.loadFragments(@IdRes contentId: Int, showPosition: Int, fragmentManager: FragmentManager,
                           vararg fragments: Fragment) {
    if (fragments.isNotEmpty()) {

        fragmentManager.beginTransaction().apply {
            for (index in fragments.indices) {
                val fragment = fragments[index]
                if (index == showPosition) {
                    add(contentId, fragment, fragment.javaClass.canonicalName)
                    setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                } else {
                    hide(fragment)
                    setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                }
            }
        }.commit()

    } else {
        throw IllegalArgumentException("fragments is empty")
    }
}


fun Activity.showHideFragments(fragmentManager: FragmentManager, showFragment: Fragment) {
    fragmentManager.beginTransaction().apply {
        show(showFragment)
        setMaxLifecycle(showFragment, Lifecycle.State.RESUMED)

        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != showFragment) {
                hide(fragment)
                setMaxLifecycle(fragment, Lifecycle.State.STARTED)
            }
        }

    }.commit()
}
