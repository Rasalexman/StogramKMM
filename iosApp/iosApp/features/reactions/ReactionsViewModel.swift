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
    
    override init() {
        super.init()
        reactions = ReactionEntity.companion.createRandomList()
    }
    
}
