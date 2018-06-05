package com.xd.demi.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xd.demi.R


class AFragment : Fragment() {
    private val TAG = "Activity生命周期"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "AFragment onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "AFragment onCreateView")
        return inflater.inflate(R.layout.layout_afragment, container, false);
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.i(TAG, "AFragment onAttach")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "AFragment onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "AFragment onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "AFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "AFragment onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "AFragment onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "AFragment onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "AFragment onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "AFragment onDetach")
    }
}