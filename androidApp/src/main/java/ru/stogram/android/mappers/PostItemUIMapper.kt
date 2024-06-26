package ru.stogram.android.mappers

import com.rasalexman.sresult.common.utils.IMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.PostEntity
import ru.stogram.utils.getRandomPhoto

class PostItemUIMapper(
    private val userUIMapper: IUserUIMapper
) : IPostItemUIMapper {

    override suspend fun convert(from: PostEntity): PostItemUI {
        return convertSingle(from)
    }

    override fun convertSingle(from: PostEntity): PostItemUI {
        val postContent = from.takeContent()
        val postUser = userUIMapper.convertSingle(from.takePostUser())
        return PostItemUI(
            id = from.id,
            postId = from.postId,
            text = from.text,
            likesCount = from.likesCount,
            commentsCount = from.commentsCount,
            date = from.date,
            user = postUser,
            firstPhoto = postContent.firstOrNull() ?: getRandomPhoto(),
            postContent = postContent,
            isLiked = from.isLiked
        )
    }
}

interface IPostItemUIMapper : IMapper<PostEntity, PostItemUI> {
    fun convertSingle(from: PostEntity): PostItemUI
}
