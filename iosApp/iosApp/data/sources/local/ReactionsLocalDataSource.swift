//
//  LikesLocalDataSource.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation

final class ReactionsLocalDataSource : IReactionsLocalDataSource {
    
    private var userReactions: [ReactionModel] = []
    
    func takeUserReactions() -> [ReactionModel] {
        if userReactions.isEmpty {
            userReactions = createReactions()
        }
        
        return userReactions
    }
    
    private func createReactions() -> [ReactionModel] {
        var createdReactions:[ReactionModel] = []
        let randomInt = Int.random(in: 20..<48)
        for rnd in 0...randomInt {
            createdReactions.append(
                ReactionModel(
                    reactionType: randomReaction(),
                    userName: randomUserName(),
                    userPhoto: randomPhoto(),
                    reactionUserPhoto: randomPhoto(),
                    liked: rnd%2==0,
                    comment: randomString(100)
                )
            )
        }
        return createdReactions
    }
}

protocol IReactionsLocalDataSource {
    func takeUserReactions() -> [ReactionModel]
}
