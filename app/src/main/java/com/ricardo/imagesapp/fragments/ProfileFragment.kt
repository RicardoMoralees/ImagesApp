package com.ricardo.imagesapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.ricardo.imagesapp.R
import com.ricardo.imagesapp.adapters.HomeImageAdapter
import com.ricardo.imagesapp.adapters.SimpleImageAdapter
import com.ricardo.imagesapp.models.Image
import com.ricardo.imagesapp.models.User
import com.ricardo.imagesapp.services.ImageServices
import com.ricardo.imagesapp.services.ProfileService
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), ProfileService.ProfileInterface, ImageServices.ImagesInterface {

    lateinit var user: User
    var username: String = ""
    var page: Int = 1;
    var isLoadding: Boolean = false;
    var imagenes: List<Image> = ArrayList<Image>()
    lateinit var adapter: SimpleImageAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        iv_back.setOnClickListener {
            getActivity()?.supportFragmentManager?.popBackStack();
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        startService()
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
                ProfileFragment().apply {
                    this.username = username
                }
    }

    fun startService() {
        ProfileService.getUserProfile(context!!, username,this)
        ImageServices.getUserPhotos(context!!, username, 1, this)
    }

    fun setContent(){
        Glide.with(context!!).load(user.profile_image?.large).apply(RequestOptions.circleCropTransform()).into(image_profile)
        tv_profile_name.text = user.name
        tv_profile_bio.text = if (user.bio.isNullOrEmpty()) getString(R.string.no_disponible) else user.bio
        profile_number_photos.text = user.total_photos.toString()
        profile_number_collections.text = user.total_collections.toString()
        profile_number_likes.text = user.total_likes.toString()
        location.text = if (user.location.isNullOrEmpty()) getString(R.string.no_disponible) else user.location
    }

    fun setRecycler() {
        if (page <= 1) {
            adapter = SimpleImageAdapter(imagenes)
            linearLayoutManager = LinearLayoutManager(context)
            rv_profile_photos.layoutManager = linearLayoutManager
            rv_profile_photos.adapter = adapter

            rv_profile_photos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) && !isLoadding) {
                        page++
                        isLoadding = true
                        load_images_profile.visibility = View.VISIBLE
                        startService()
                    }
                }
            })
        }
    }

    override fun onProfileResponse(user: User) {
        this.user = user
        setContent()
        progress_profile.visibility = View.GONE
        profile_container.visibility = View.VISIBLE
    }

    override fun onProfileFail() {
    }

    override fun onImagesResponse(images: List<Image>) {
        this.imagenes += images
        isLoadding = false
        load_images_profile.visibility = View.GONE
        adapter.images = imagenes
        adapter.notifyDataSetChanged()
    }

    override fun onImagesFail() {
        isLoadding = false
        load_images_profile.visibility = View.GONE
        Toast.makeText(context, "Images Fail", Toast.LENGTH_SHORT).show()
    }
}
