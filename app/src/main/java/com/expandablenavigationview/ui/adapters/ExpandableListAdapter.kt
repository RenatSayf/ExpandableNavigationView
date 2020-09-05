package com.expandablenavigationview.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.expandablenavigationview.R


class ExpandableListAdapter(private val context: Context) : BaseExpandableListAdapter()
{
    private var listDataHeader: ArrayList<String> = arrayListOf()
    private var listDataIcons: ArrayList<Drawable> = arrayListOf()
    private var listDataChild: HashMap<String, List<String>> = HashMap()

    init {
        //Creating menu structure

        // Adding data header
        listDataHeader.add("Home")
        listDataHeader.add("Camera")
        listDataHeader.add("Gallery")
        listDataHeader.add("Slideshow")

        // Adding data icons
        ContextCompat.getDrawable(context, R.drawable.ic_home)?.let { listDataIcons.add(it) }
        ContextCompat.getDrawable(context, R.drawable.ic_menu_camera)?.let { listDataIcons.add(it) }
        ContextCompat.getDrawable(context, R.drawable.ic_menu_gallery)?.let { listDataIcons.add(it) }
        ContextCompat.getDrawable(context, R.drawable.ic_menu_slideshow)?.let { listDataIcons.add(it) }

        // Adding child data
        val homeHeader: ArrayList<String> = arrayListOf()

        val cameraHeader: ArrayList<String> = arrayListOf()
        cameraHeader.add("Red camera")
        cameraHeader.add("Green camera")

        val galleryHeader: ArrayList<String> = arrayListOf()
        galleryHeader.add("Pictures")
        galleryHeader.add("Documents")

        val slideHeader: ArrayList<String> = arrayListOf()
        slideHeader.add("My slides")
        slideHeader.add("Slides of my friends")

        listDataChild.put(listDataHeader[0], homeHeader) // Header, Child data
        listDataChild.put(listDataHeader[1], cameraHeader)
        listDataChild.put(listDataHeader[2], galleryHeader)
        listDataChild.put(listDataHeader[3], slideHeader)
    }

    override fun getGroupCount(): Int
    {
        return listDataHeader.size
    }

    override fun getChildrenCount(index: Int): Int
    {
        return listDataChild[listDataHeader[index]]?.size ?: 0
    }

    override fun getGroup(index: Int): Any
    {
        return listDataHeader[index]
    }

    override fun getChild(groupIndex: Int, childIndex: Int): Any
    {
        return listDataChild[listDataHeader[groupIndex]]!![childIndex]
    }

    override fun getGroupId(index: Int): Long
    {
        return index.toLong()
    }

    override fun getChildId(groupIndex: Int, childIndex: Int): Long
    {
        return childIndex.toLong()
    }

    override fun hasStableIds(): Boolean
    {
        return false
    }

    override fun getGroupView(groupIndex: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View
    {
        var groupView = convertView
        if (groupView == null)
        {
            groupView = LayoutInflater.from(context).inflate(R.layout.listheader, parent, false)
        }
        val iconView = groupView?.findViewById<ImageView>(R.id.iconView)
        iconView?.apply {
            setImageDrawable(listDataIcons[groupIndex])
        }
        val textView = groupView?.findViewById<TextView>(R.id.titleView)
        textView?.apply {
            text = getGroup(groupIndex) as String
        }
        val indicatorView = groupView?.findViewById<ImageView>(R.id.indicatorView)
        when {
            isExpanded && getChildrenCount(groupIndex) > 0 ->
            {
                indicatorView?.let {
                    it.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_drop_up))
                    it.visibility = View.VISIBLE
                }
            }
            !isExpanded && getChildrenCount(groupIndex) > 0 ->
            {
                indicatorView?.let {
                    it.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_arrow_drop_down))
                    it.visibility = View.VISIBLE
                }
            }
            //if the submenu is empty, then hide indicator
            getChildrenCount(groupIndex) == 0 ->
            {
                indicatorView?.visibility = View.INVISIBLE
            }
        }
        return groupView!!
    }

    override fun getChildView(groupIndex: Int, childindex: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View
    {
        val childText = getChild(groupIndex, childindex) as String
        var childView = convertView
        if (childView == null)
        {
            childView = LayoutInflater.from(context).inflate(R.layout.list_submenu, parent, false)
        }
        val textView = childView?.findViewById<TextView>(R.id.childView)
        textView?.text = childText

        return childView!!
    }

    override fun isChildSelectable(groupIndex: Int, childindex: Int): Boolean
    {
        return true
    }


}