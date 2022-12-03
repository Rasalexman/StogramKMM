package ru.stogram.models

import io.realm.kotlin.types.RealmObject
import ru.stogram.utils.getRandomString

class LastUserEntity : RealmObject {
    var id: String = getRandomString(100)
    var userId: String = ""
    var sessionLogin: String = ""
}