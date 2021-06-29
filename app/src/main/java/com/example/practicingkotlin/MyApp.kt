package com.example.practicingkotlin

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration =
            RealmConfiguration.Builder().name("TodoList.db").deleteRealmIfMigrationNeeded()
                .schemaVersion(0).build()

        Realm.setDefaultConfiguration(configuration)
    }
}