package com.ricardo.imagesapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ricardo.imagesapp.R
import io.realm.Realm
import io.realm.RealmConfiguration

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded() .build()
        Realm.setDefaultConfiguration(config)
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { _ ->
            Realm.getDefaultInstance().deleteAll()
        }
        Thread.sleep(3000)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
