package ru.stogram.android.constants

import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.models.CommentItemUI
import ru.stogram.android.models.PostItemUI
import ru.stogram.android.models.ReactionItemUI
import ru.stogram.models.CommentEntity
import ru.stogram.models.IUser
import ru.stogram.models.PostEntity
import ru.stogram.models.ReactionEntity

typealias PostsResult = SResult<List<PostItemUI>>
typealias PostDetailsResult = SResult<PostItemUI>
typealias StoriesResult = SResult<List<IUser>>
typealias UserResult = SResult<IUser>
typealias ReactionsResult = SResult<List<ReactionItemUI>>
typealias CommentsResult = SResult<List<CommentItemUI>>