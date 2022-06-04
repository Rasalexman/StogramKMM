//
//  CommentsViewModel.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import Foundation
import Sodi
import shared

final class CommentsViewModel : BaseViewModel {
    
    private let repository: ICommentsRepository = instance()
    
    @Published var comments: [CommentEntity] = []
    
    func start(selectedPostId: String) {
        addObserver(repository.getAllCommentsAsCommonFlow(postId: selectedPostId).watch { result in
            if let postComments = result as? [CommentEntity] {
                self.comments = postComments
            }
        })
    }
}
