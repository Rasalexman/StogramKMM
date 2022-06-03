//
//  CommentsRepository.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import Foundation
import shared

final class CommentsRepository : ICommentsRepository {
    private let localDataSource: ICommentsLocalDataSource
    init(lds: ICommentsLocalDataSource) {
        localDataSource = lds
    }
    
    func takePostComments() -> [CommentEntity] {
        return localDataSource.takePostComments()
    }
}

protocol ICommentsRepository {
    func takePostComments() -> [CommentEntity]
}
