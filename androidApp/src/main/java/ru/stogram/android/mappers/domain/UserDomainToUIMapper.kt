package ru.stogram.android.mappers.domain

import com.rasalexman.sresult.common.utils.IMapper
import ru.stogram.android.models.UserUI
import ru.stogram.models.IUser
import ru.stogram.models.domain.UserModel

class UserDomainToUIMapper : IUserDomainToUIMapper {
    override suspend fun convert(from: UserModel): UserUI {
        return convertSingle(from)
    }

    override fun convertSingle(from: UserModel): UserUI {
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

interface IUserDomainToUIMapper : IMapper<UserModel, UserUI> {
    fun convertSingle(from: UserModel): UserUI
}