//
//  CommentsViewModel.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import Foundation
import Sodi

final class CommentsViewModel : BaseViewModel {
    
    private let repository: ICommentsRepository = instance()
    @Published var postComments: [CommentModel] = []
    
    override init() {
        postComments = repository.takePostComments()
    }
}
