package ru.stogram.android.mappers

import com.rasalexman.sresult.common.utils.IMapper
import ru.stogram.android.models.CommentItemUI
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.CommentEntity
import ru.stogram.models.PostEntity
import ru.stogram.utils.getRandomPhoto

class CommentItemUIMapper : ICommentItemUIMapper {

    override suspend fun convert(from: CommentEntity): CommentItemUI {
        return convertSingle(from)
    }

    override fun convertSingle(from: CommentEntity): CommentItemUI {
        return CommentItemUI(
            id = from.id,
            postId = from.postId,
            text = from.text,
            likesCount = from.likesCount,
            date = from.date,
            user = from.takeCommentUser(),
            isLiked = from.isLiked
        )
    }
}

interface ICommentItemUIMapper : IMapper<CommentEntity, CommentItemUI> {
    fun convertSingle(from: CommentEntity): CommentItemUI
}
