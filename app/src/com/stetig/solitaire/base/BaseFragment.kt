package com.stetig.callsync.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.stetig.solitaire.R
import com.stetig.solitaire.activity.MainActivity

/**
 * Created by Pratik Kataria on 18-11-2020.
 */
abstract class BaseFragment : Fragment(), View.OnClickListener, OnRefreshListener {

    abstract fun initView(rootView: View?)
    var mActivity: MainActivity? = null

    fun replaceFragment(fragment: Fragment, container: Int, isBackStack: Boolean) {
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(container, fragment)
        if (isBackStack) {
            val stack_name = fragment.javaClass.name
            transaction.addToBackStack(stack_name)
        }
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, isBackStack: Boolean) {
        val manager = requireActivity().supportFragmentManager
        val transaction = manager.beginTransaction()
        // transaction.replace(R.id.home_layout_container,fragment);
        if (isBackStack) {
            val stack_name = fragment.javaClass.name
            transaction.addToBackStack(stack_name)
        }
        transaction.commit()
    }

    public fun navigateTo(navigationId: Int) {
        val navHostController = NavHostFragment.findNavController(this)
        navHostController.navigate(navigationId)
        mActivity?.hideNoDataText()
    }

    public fun navigateTo(navigationId: Int, bundle: Bundle) {
        val navHostController = NavHostFragment.findNavController(this)
        navHostController.navigate(navigationId, bundle)
        mActivity?.hideNoDataText()
    }

    fun onBackPressed(): Boolean {
        return false
    }

    override fun onPause() {
        super.onPause()
        mActivity?.hideNoDataText()
    }

    @CallSuper
    override fun onClick(view: View) {
        if (view.id == R.id.thinBackBtn) mActivity?.onBackPressed()
    }

    override fun onRefresh() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mActivity = context
        }
    }

    abstract fun setContentView(activityMain: Int)
}
