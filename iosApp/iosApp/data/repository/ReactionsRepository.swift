//
//  LikesRepository.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import shared

final class ReactionsRepository : IReactionsRepository {
    private let localDataSource: IReactionsLocalDataSource
    init(lds: IReactionsLocalDataSource) {
        localDataSource = lds
    }
    
    func takeUserReactions() -> [ReactionEntity] {
        return localDataSource.takeUserReactions()
    }
}

protocol IReactionsRepository {
    func takeUserReactions() -> [ReactionEntity]
}
