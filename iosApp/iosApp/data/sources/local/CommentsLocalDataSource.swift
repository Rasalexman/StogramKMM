//
//  CommentsLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import Foundation

final class CommentsLocalDataSource : ICommentsLocalDataSource {
    
    func takePostComments() -> [CommentModel] {
        var createdComments:[CommentModel] = []
        let randomInt = Int.random(in: 20..<48)
        for _ in 0...randomInt {
            createdComments.append(CommentModel())
        }
        
        return createdComments
    }
}

protocol ICommentsLocalDataSource {
    func takePostComments() -> [CommentModel]
}
