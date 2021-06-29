package com.example.practicingkotlin.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Notes : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var title: String? = null
    var description: String? = null
}


