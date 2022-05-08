//
//  CommentsRepository.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import Foundation

final class CommentsRepository : ICommentsRepository {
    private let localDataSource: ICommentsLocalDataSource
    init(lds: ICommentsLocalDataSource) {
        localDataSource = lds
    }
    
    func takePostComments() -> [CommentModel] {
        return localDataSource.takePostComments()
    }
}

protocol ICommentsRepository {
    func takePostComments() -> [CommentModel]
}
