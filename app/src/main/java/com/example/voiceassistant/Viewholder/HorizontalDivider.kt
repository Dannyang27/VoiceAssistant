package com.example.voiceassistant.Viewholder

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.example.voiceassistant.R

class HorizontalDivider(context: Context): RecyclerView.ItemDecoration(){
    private var mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider)

    override fun onDrawOver(c : Canvas, parent: RecyclerView, state : RecyclerView.State){
        val left = parent.paddingLeft + 20
        val right = parent.width - (parent.paddingRight + 20)

        val childCount = parent.childCount

        repeat(childCount){
            val child = parent.getChildAt(it)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight

            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }


}