//
//  CommentModel.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import Foundation

struct CommentModel: Identifiable, IPostModel {
    let id = UUID()
    var commentId: String = UUID().uuidString
    var postId: String = UUID().uuidString
    var userId: String = UUID().uuidString
    var userName: String = Consts.USER_NAME
    var userPhoto: String = Consts.USER_PHOTO
    var isLiked: Bool = false
    var text: String = ""
    var likesCount: String = "56"
    var date: String = "03.05.2022 21:55"
    var hasStory: Bool = false
    
    init() {
        userName = randomUserName()
        userPhoto = randomPhoto()
        isLiked = randomBool
        likesCount = randomCount
        text = randomString(300)
    }
}
