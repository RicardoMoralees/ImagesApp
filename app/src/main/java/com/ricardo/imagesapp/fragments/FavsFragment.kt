package com.ricardo.imagesapp.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ricardo.imagesapp.R
import com.ricardo.imagesapp.adapters.HomeImageAdapter
import com.ricardo.imagesapp.adapters.HomeImagesViewHolder
import com.ricardo.imagesapp.models.Image
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_favs.*

class FavsFragment : Fragment() {

    var page: Int = 1;
    var isLoadding: Boolean = false;
    var imagenes: List<Image> = ArrayList<Image>()
    lateinit var adapter: HomeImageAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var imageAdapterInterface: HomeImagesViewHolder.ImageAdapterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        getFavs()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }
    companion object {
        @JvmStatic
        fun newInstance(imagesInterface: HomeImagesViewHolder.ImageAdapterInterface) =
                FavsFragment().apply {
                        this.imageAdapterInterface = imagesInterface
                    }
    }

    fun getFavs() {
        val realm = Realm.getDefaultInstance()
        val results = realm.where<Image>().equalTo("esFavorita", true).findAll()
        val favs = realm.copyFromRealm(results)
        this.imagenes = favs
        adapter.images = imagenes
        adapter.notifyDataSetChanged()

        if (favs.isNotEmpty()){
            layout_empty_favs.visibility = View.GONE
            rv_favs_images.visibility = View.VISIBLE
        } else {
            layout_empty_favs.visibility = View.VISIBLE
            rv_favs_images.visibility = View.GONE
        }
    }

    fun setRecycler() {
        if (page <= 1) {
            adapter = HomeImageAdapter(imagenes, imageAdapterInterface)
            linearLayoutManager = LinearLayoutManager(context)
            rv_favs_images.layoutManager = linearLayoutManager
            rv_favs_images.adapter = adapter
        }
    }
}
