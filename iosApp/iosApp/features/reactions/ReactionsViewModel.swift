//
//  LikesViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi
import shared

final class ReactionsViewModel : BaseViewModel {
    private let repository: IReactionsRepository = instance()
    
    @Published var reactions: [ReactionEntity] = []
    
    func start() {
        addObserver(repository.getAllReactionsAsCommonFlow().watch(block: { result in
            if let currentReactions = result as? [ReactionEntity] {
                self.reactions = currentReactions
            }
        }))
    }
    
}
