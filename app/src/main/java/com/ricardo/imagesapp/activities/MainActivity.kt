package com.ricardo.imagesapp.activities

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.ricardo.imagesapp.R
import com.ricardo.imagesapp.adapters.HomeImagesViewHolder
import com.ricardo.imagesapp.fragments.FavsFragment
import com.ricardo.imagesapp.fragments.HomeFragment
import com.ricardo.imagesapp.fragments.ProfileFragment
import com.ricardo.imagesapp.models.Image
import com.ricardo.imagesapp.utils.Constants
import com.ricardo.imagesapp.utils.Constants.Companion.PREF_NAME
import com.ricardo.imagesapp.utils.Constants.Companion.PRIVATE_MODE
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HomeImagesViewHolder.ImageAdapterInterface {

    var username: String = ""
    lateinit var selectedFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        nav_view.setOnNavigationItemSelectedListener {
            menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_home -> {
                        selectedFragment = HomeFragment.newInstance(this)
                        openFragment(selectedFragment)
                        true
                    }
                    R.id.navigation_favs -> {
                        selectedFragment = FavsFragment.newInstance(this)
                        openFragment(selectedFragment)
                        true
                    }
                    else -> false
                }
        }
        nav_view.selectedItemId = R.id.navigation_home
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onUserSelected(username: String) {
        this.username = username
        val fragment = ProfileFragment.newInstance(username)
        openFragment(fragment)
    }

    override fun onLikeSelected(isActive: Boolean, imagen: Image) {
        val realm = Realm.getDefaultInstance()
        var nuevaImagen = realm.where<Image>().equalTo("id", imagen.id).findFirst()
        realm.executeTransaction{ _ ->
            nuevaImagen?.liked_by_user = isActive
            realm.copyToRealmOrUpdate(nuevaImagen)
        }
    }

    override fun onFavSelected(isActive: Boolean, imagen: Image) {
        val realm = Realm.getDefaultInstance()
            var nuevaImagen = realm.where<Image>().equalTo("id", imagen.id).findFirst()
        realm.executeTransaction{ _ ->
            nuevaImagen?.esFavorita = isActive
            realm.copyToRealmOrUpdate(nuevaImagen)
        }
        if (nav_view.selectedItemId != R.id.navigation_home){
            (selectedFragment as FavsFragment).getFavs()
        }
    }
}
