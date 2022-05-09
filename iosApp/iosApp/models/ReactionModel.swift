//
//  LikeActionModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

struct ReactionModel: Identifiable, Hashable {
    let id = UUID()
    var reactionType: ReactionType
    var postId: String = UUID().uuidString
    var userName: String = Consts.USER_NAME
    var userPhoto: String = Consts.USER_PHOTO
    var reactionUserPhoto: String
    var liked: Bool = false
    var comment: String = ""
    
    var fullDescription: String {//AttributedString {
        var desc: String = "**\(userName)**"
        switch reactionType {
        case .photoLike:
            desc += " поставил(а) лайк на вашу фотографию"
        case .photoComment:
            desc += " оставил(а) коммент на вашу фотографию: \(comment)"
        case .historyComment:
            desc += " оставил(а) коммент на вашу историю: \(comment)"
        case .likeOnComment:
            desc += " поставил(а) лайк на ваш комментарий"
        }
        
        return desc//.markdownToAttributed()
    }
    
    var postModel: PostModel {
        let userPost = PostModel()
        userPost.userPhoto = self.userPhoto
        return userPost
    }
}

public enum ReactionType {
    case photoLike
    case photoComment
    case historyComment
    case likeOnComment
}
