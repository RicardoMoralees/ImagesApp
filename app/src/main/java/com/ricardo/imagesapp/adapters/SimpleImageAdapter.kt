package com.ricardo.imagesapp.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ricardo.imagesapp.models.Image
import com.ricardo.imagesapp.R

class SimpleImageAdapter(var images: List<Image>): RecyclerView.Adapter<SimpleImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_simple_image, parent, false)
        return SimpleImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: SimpleImagesViewHolder, position: Int) {
        return holder.bind(images[position])
    }
}

class SimpleImagesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    private val ivPhoto: ImageView = itemView.findViewById(R.id.iv_simple_image)

    fun bind(image: Image) {
        Glide.with(itemView.context).load(image.urls?.thumb).into(ivPhoto)
    }


}