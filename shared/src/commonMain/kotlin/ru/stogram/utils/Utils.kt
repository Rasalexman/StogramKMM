package ru.stogram.utils

import kotlinx.datetime.*
import ru.stogram.models.ReactionEntity
import kotlin.random.Random

private val namesCombs = listOf(
    "Ivan", "Disco", "Chillout",
    "Vocal", "Deep", "Mix", "Soundeo",
    "House", "Fabulous", "SoundCloud",
    "However", "Catalina", "Xcode",
    "Product", "Overview", "Public",
    "Session", "Disconnect", "Respond",
    "Request", "Dapp", "Kotlin",
    "Parabéns", "Really", "Belissimo", "Beautiful"
)

private val randomImages = listOf(
    "https://sun9-17.userapi.com/s/v1/if1/gFvSSXX6538NAEnF4pfFZfEGm_VFUdTkffcNBtzxNMusdQZi6OFcJBljuTqhOZb6GgyxobAD.jpg?size=1620x2160&quality=96&type=album",
    "https://sun9-57.userapi.com/s/v1/if1/6xLsul8GX9De6wI_cs_iWiSYDGRgoLUyiqyozXmDZitvH_r_cvF8B1CZSAu2dRZHQ0TI5c7l.jpg?size=959x1280&quality=96&type=album",
    "https://sun9-57.userapi.com/s/v1/if2/-ZfdJYLdZ0VXD35eImf7zacqNXCcVPaPtc8kFIF_TkNZfF03pijZIBujmZQkhaTtgbzimuDhZbRmklrla7VWVItL.jpg?size=2046x2048&quality=96&type=album",
    "https://sun9-20.userapi.com/s/v1/if2/Sa_U8U0SslDmzGKiOAb5tl03miJqiMeVu8q-6j4mW-G0KDFcHECI8cPChBqg73IEdFkVU0bDoWySEh_mre7ql2-t.jpg?size=1440x1920&quality=96&type=album",
    "https://sun9-55.userapi.com/s/v1/if2/ck95em4IS-RY7FQt-AFmAsWvdaaPGtkAJhOPUWVaGR4NlFj4W5a0L_W__Qc40Kqx-XzbcoXUVcofkaKPe4kitpWW.jpg?size=1440x1920&quality=96&type=album",
    "https://sun9-68.userapi.com/s/v1/if2/IO1SJfeql8Hft95xwz5mtIpLLt7gbGJyjOA7ZSTWLRDEIOP7fdtdhbBy0lrSGxgUpIL2jWRx0SlsFlLTfJPRzoVo.jpg?size=1440x1920&quality=96&type=album",
    "https://sun9-8.userapi.com/s/v1/if2/DdhgYHSsFPyenYP22DBp0xyrmZIsn-Qm9k5XSYAQI6VgBwpqZfNxnrrPwU_Bh9UTneNSUEZPmZdyz08KfEEeGqvI.jpg?size=1920x1440&quality=96&type=album",
    "https://sun9-84.userapi.com/s/v1/if2/OnEaIIRslc9QkJbtt3znqNzDDTx-W9qhOAI1l8IU8lRFg0-GJ36YZIzB7T6sv0Tdh4hp0JExUlI4-D-SdYyQRIJO.jpg?size=1440x1920&quality=96&type=album",
    "https://sun9-45.userapi.com/s/v1/if2/B15YhBO5zQacYMCd6-6ESyRE5C9B0HXI4ErlXBftt7dGayiJ3hcTOUgOvTZBKGrVKo1_BceEpWrt34BZ7hCdjxs8.jpg?size=958x1280&quality=96&type=album",
    "https://sun9-49.userapi.com/s/v1/if1/mxRHBT4W1eZFXcJqEuVBIz1zMPFwA0KDYAuEThsQtJAXYR4b57qBzr0wOoN5mJVyjy41F4BK.jpg?size=960x1280&quality=96&type=album",
    "https://sun9-10.userapi.com/s/v1/if1/biahgdZu1qPz-VlYgbkJS8kmS8__12bNSvU63f_4dSA8iDJdur5hbIeFBqH4oB44Di_Blrj3.jpg?size=959x1280&quality=96&type=album",
    "https://sun9-56.userapi.com/s/v1/if2/oZEuioWZqO0D9K1IEacHlGxokiBR6LBe_E2iM_a5e1e_p6UUZf5_cdJDi7GQ-QajvhOPXFDmzS7UJL7-tiSkzvpD.jpg?size=1022x1280&quality=96&type=album",
    "https://sun9-55.userapi.com/s/v1/if1/1kHxBaiuYe9ognPFcpPgZY4tw84YPb_0RwJJWAAT2CvHVMU6lq9MO2gRWxkmX1z19HCGb0Qa.jpg?size=730x730&quality=96&type=album",
    "https://sun9-78.userapi.com/s/v1/if1/NJMnkmL46zFogqFOotTBPV_sAedT-LH6yrCzj18TD-Jp_NaaJXJ4JJOHTt1519kT0Aq52obH.jpg?size=730x730&quality=96&type=album",
    "https://sun9-85.userapi.com/s/v1/if1/vvw3gV1UVsPpwJsj0xFJWLNy9Cbh-M7hw1_axNPEddiD0mSfoyYzPemFrKBPc72ltnG9_0wo.jpg?size=730x730&quality=96&type=album",
    "https://sun9-23.userapi.com/s/v1/if1/v-Uz72F93Wg9rjUo6zKpPLh8shebo_E5DePl73eqY7cXpNfPRrcitC5EbiQRQXmfID7MXkuU.jpg?size=730x730&quality=96&type=album"
)

private val reactionsTypes = listOf(
    ReactionEntity.photoComment,
    ReactionEntity.likeOnComment,
    ReactionEntity.photoLike,
    ReactionEntity.historyComment,
)

private val reactionsTypeDates = listOf(
    "вчера",
    "3 часа назад",
    "в 21:34",
    "сегодня в 10:30",
    "5 дней назад"
)

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

val randomCount: String
    get() = Random.nextInt(10, 99999).toString()

val randomBool: Boolean
    get() = Random.nextInt(0, 2)%2==0

val randomLocation: String
    get() = if (randomBool) "Saint-Petersburg" else ""

fun getRandomName(): String {
    val randomCount: Int = Random.nextInt(0, namesCombs.size-1)
    val firstName = namesCombs[randomCount]
    val distinctNames = namesCombs.filter { it != firstName }
    val randomCount2: Int = Random.nextInt(0, distinctNames.size-1)
    val secondName = distinctNames[randomCount2]
    return "$firstName $secondName"
}

fun getRandomPhoto(): String {
    val randomCount: Int = Random.nextInt(0, randomImages.size-1)
    return randomImages[randomCount]
}

fun getRandomPhotoList(): List<String> {
    val randomPhotos = mutableSetOf<String>()
    val randomCount: Int = Random.nextInt(1, 7)
    repeat(randomCount) {
        randomPhotos.add(getRandomPhoto())
    }
    return randomPhotos.toList()
}

fun getRandomReactionType(): String {
    val randomCount: Int = Random.nextInt(0, reactionsTypes.size-1)
    return reactionsTypes[randomCount]
}

fun getRandomReactionDateText(): String {
    val randomCount: Int = Random.nextInt(0, reactionsTypeDates.size-1)
    return reactionsTypeDates[randomCount]
}

fun getRandomDate(): String {
    val randBackTime = Random.nextInt(10, 99999)
    val now = Clock.System.now()
    val systemTZ = TimeZone.currentSystemDefault()
    val lastDt = now.minus(randBackTime, DateTimeUnit.MILLISECOND, systemTZ)
    val dt = lastDt.toLocalDateTime(systemTZ)
    return dt.toString()
}