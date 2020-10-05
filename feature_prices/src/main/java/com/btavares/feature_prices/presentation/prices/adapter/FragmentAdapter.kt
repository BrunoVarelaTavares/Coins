package com.btavares.feature_prices.presentation.prices.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

import androidx.viewpager2.adapter.FragmentStateAdapter

import com.btavares.feature_prices.presentation.prices.fragments.AllAssetsFragment
import com.btavares.feature_prices.presentation.prices.fragments.FragmentTopGainers
import com.btavares.feature_prices.presentation.prices.fragments.FragmentTopLosers

internal class FragmentAdapter(
    var context: Context,
    fm: FragmentManager?,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fm!!, lifecycle) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllAssetsFragment()
            }
            1 -> {
                FragmentTopGainers()
            }
            2 -> {
                FragmentTopLosers()
            }
            else -> createFragment(position)
        }
    }
}