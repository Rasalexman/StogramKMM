//
//  LikesViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi

final class ReactionsViewModel : BaseViewModel {
    private let repository: IReactionsRepository = instance()
    
    @Published var reactions: [ReactionModel] = []
    
    override init() {
        super.init()
        reactions = repository.takeUserReactions()
    }
    
}
