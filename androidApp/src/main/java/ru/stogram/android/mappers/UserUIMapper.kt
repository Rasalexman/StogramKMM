package ru.stogram.android.mappers

import com.rasalexman.sresult.common.utils.IMapper
import ru.stogram.android.models.UserUI
import ru.stogram.models.IUser

class UserUIMapper : IUserUIMapper {
    override suspend fun convert(from: IUser): UserUI {
        return convertSingle(from)
    }

    override fun convertSingle(from: IUser): UserUI {
        return UserUI(
            id = from.id,
            name = from.name,
            login = from.login,
            photo = from.photo,
            desc = from.desc,
            password = from.password,
            hasStory = from.hasStory,
            bio = from.bio,
            isCurrentUser = from.isCurrentUser,
            isSubscribed = from.isSubscribed,
            subsCount = from
                .subsCount,
            observCount = from.observCount
        )
    }
}

interface IUserUIMapper : IMapper<IUser, UserUI> {
    fun convertSingle(from: IUser): UserUI
}