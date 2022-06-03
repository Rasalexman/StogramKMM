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
    
    //private let repository: ICommentsRepository = instance()
    @Published var postComments: [CommentEntity] = []
    
    override init() {
        postComments = CommentEntity.companion.createRandomList()
    }
}
