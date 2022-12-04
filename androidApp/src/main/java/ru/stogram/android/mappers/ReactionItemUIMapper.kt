package ru.stogram.android.mappers

import com.rasalexman.sresult.common.utils.IMapper
import ru.stogram.android.models.ReactionItemUI
import ru.stogram.models.ReactionEntity
import javax.inject.Inject

class ReactionItemUIMapper @Inject constructor(
    private val postItemUIMapper: IPostItemUIMapper
) : IReactionItemUIMapper {

    override suspend fun convert(from: ReactionEntity): ReactionItemUI {
        return convertSingle(from)
    }

    override fun convertSingle(from: ReactionEntity): ReactionItemUI {
        return ReactionItemUI(
            id = from.id,
            type = from.type,
            post = postItemUIMapper.convertSingle(from.takeReactionPost()),
            user = from.takeUserFrom(),
            comment = from.comment,
            liked = from.liked,
            date = from.date,
            description = from.createFullDescription()
        )
    }
}

interface IReactionItemUIMapper : IMapper<ReactionEntity, ReactionItemUI> {
    fun convertSingle(from: ReactionEntity): ReactionItemUI
}