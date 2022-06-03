//
//  LikesLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import shared

final class ReactionsLocalDataSource : IReactionsLocalDataSource {
    
    private var userReactions: [ReactionEntity] = []
    
    func takeUserReactions() -> [ReactionEntity] {
        if userReactions.isEmpty {
            userReactions = ReactionEntity.companion.createRandomList()
        }
        
        return userReactions
    }
}

protocol IReactionsLocalDataSource {
    func takeUserReactions() -> [ReactionEntity]
}
