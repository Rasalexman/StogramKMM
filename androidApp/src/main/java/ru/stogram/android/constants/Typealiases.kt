package ru.stogram.android.constants

import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.models.IUser
import ru.stogram.models.PostEntity

typealias PostsResult = SResult<List<PostEntity>>
typealias StoriesResult = SResult<List<IUser>>