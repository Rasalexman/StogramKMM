//
//  CommentsLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import Foundation
import shared

final class CommentsLocalDataSource : ICommentsLocalDataSource {
    
    func takePostComments() -> [CommentEntity] {
        return CommentEntity.companion.createRandomList()
    }
}

protocol ICommentsLocalDataSource {
    func takePostComments() -> [CommentEntity]
}
