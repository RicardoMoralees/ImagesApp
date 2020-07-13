package com.ricardo.imagesapp.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.ricardo.imagesapp.R
import com.ricardo.imagesapp.adapters.HomeImageAdapter
import com.ricardo.imagesapp.adapters.HomeImagesViewHolder
import com.ricardo.imagesapp.models.Image
import com.ricardo.imagesapp.models.User
import com.ricardo.imagesapp.services.ImageServices
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), ImageServices.ImagesInterface {

    var page: Int = 1;
    var isLoadding: Boolean = false;
    var imagenes: List<Image> = ArrayList<Image>()
    lateinit var adapter: HomeImageAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var imageAdapterInterface: HomeImagesViewHolder.ImageAdapterInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        if (imagenes.isNotEmpty()) {
            load_images.visibility = View.GONE
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getRealmImages()
        if (imagenes.isEmpty()) {
            startService(page)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(homeInterface: HomeImagesViewHolder.ImageAdapterInterface) =
                HomeFragment().apply {
                    this.imageAdapterInterface = homeInterface
                }
    }

    fun startService(page: Int) {
        ImageServices.getHomeImages(context!!, page,this)
    }

    fun getRealmImages() {
        val realm = Realm.getDefaultInstance()
        val results = realm.where<Image>().findAll()
        realm.executeTransaction{_ ->
            imagenes = realm.copyFromRealm(results)
        }
    }

    fun setRecycler() {
        if (page <= 1) {
            adapter = HomeImageAdapter(imagenes, imageAdapterInterface)
            linearLayoutManager = LinearLayoutManager(context)
            rv_home_images.layoutManager = linearLayoutManager
            rv_home_images.adapter = adapter

            rv_home_images.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) && !isLoadding) {
                        page++
                        isLoadding = true
                        load_images.visibility = View.VISIBLE
                        startService(page)
                    }
                }
            })
        }
    }

    override fun onImagesResponse(images: List<Image>) {
        refreshList()
    }

    override fun onImagesFail() {
        isLoadding = false
        load_images.visibility = View.GONE
        Toast.makeText(context, "Images Fail", Toast.LENGTH_SHORT).show()
    }

    fun refreshList() {
        getRealmImages()
        isLoadding = false
        load_images.visibility = View.GONE
        adapter.images = imagenes
        adapter.notifyDataSetChanged()
    }

}
