package ru.stogram.android.mappers.domain

import com.rasalexman.sresult.common.utils.IMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.domain.PostModel
import ru.stogram.utils.getRandomPhoto

class PostDomainToUIMapper(
    private val userUIMapper: IUserDomainToUIMapper
) : IPostDomainToUIMapper {

    override suspend fun convert(from: PostModel): PostItemUI {
        return convertSingle(from)
    }

    override fun convertSingle(from: PostModel): PostItemUI {
        val postContent = from.content
        val postUser = userUIMapper.convertSingle(from.user)
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

interface IPostDomainToUIMapper : IMapper<PostModel, PostItemUI> {
    fun convertSingle(from: PostModel): PostItemUI
}
