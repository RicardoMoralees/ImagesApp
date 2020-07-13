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

class HomeImageAdapter(var images: List<Image>, val callback: HomeImagesViewHolder.ImageAdapterInterface): RecyclerView.Adapter<HomeImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return HomeImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: HomeImagesViewHolder, position: Int) {
        val imagen = images.get(position)
        holder.layout_profile_container.setOnClickListener { _ ->
            callback.onUserSelected(imagen.user?.username.toString())
        }
        holder.ivPhoto.setOnClickListener { _ ->
            callback.onUserSelected(imagen.user?.username.toString())
        }
        holder.ivFav.setOnClickListener{_ ->
            callback.onFavSelected(holder.ivFav.isChecked, imagen)
        }
        holder.ivLike.setOnClickListener{_ ->
            callback.onLikeSelected(holder.ivFav.isChecked, imagen)
        }
        return holder.bind(images[position])
    }
}

class HomeImagesViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
    val ivPhoto: ImageView = itemView.findViewById(R.id.iv_image)
    val ivLike: AppCompatCheckBox = itemView.findViewById(R.id.iv_like)
    val ivFav: AppCompatCheckBox = itemView.findViewById(R.id.iv_fav)
    private val tvProfile: TextView = itemView.findViewById(R.id.tv_profile_name)
    private val tvDescription:TextView = itemView.findViewById(R.id.tv_photo_description)
    private val tvLikes:TextView = itemView.findViewById(R.id.tv_likes)
    private val ivProfile:ImageView = itemView.findViewById(R.id.iv_profile)
    val layout_profile_container:LinearLayout = itemView.findViewById(R.id.layout_profile_container)

    fun bind(image: Image) {
        Glide.with(itemView.context).load(image.urls?.small).into(ivPhoto)
        Glide.with(itemView.context).load(image.user?.profile_image?.small).apply(RequestOptions.circleCropTransform()).into(ivProfile)
        var desc = ""
        if (image.description.isNullOrBlank()){
            desc = image.alt_description
        } else {
            desc = image.description
        }
        tvDescription.text = desc
        tvProfile.text = image.user?.username
        tvLikes.text = image.likes.toString() + " likes"
        if (image.esFavorita){
            ivFav.isChecked = true
        }
        if (image.liked_by_user){
            ivLike.isChecked = true
        }
    }

    interface ImageAdapterInterface {
        fun onUserSelected(username: String)
        fun onLikeSelected(isActive: Boolean, imagen:Image)
        fun onFavSelected(isActive: Boolean, imagen:Image)
    }

}