package com.rajnish.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.rajnish.myapplication.db.Notes
import com.squareup.picasso.Picasso


class CustomAdapter(context: Context, private val itemList: ArrayList<Notes>) :
    ArrayAdapter<Notes>(context, R.layout.list_item, itemList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val currentItem = itemList[position]

        // Set the data to the views in your list item layout
        val tvName = itemView?.findViewById<TextView>(R.id.tvName)


        val tvMobileNumber = itemView?.findViewById<TextView>(R.id.tvMobileNumber)


        tvName?.text = currentItem.title
        tvMobileNumber?.text = currentItem.Discription




        return itemView!!
    }
}
