//
//  CreatePostViewModel.swift
//  iosApp
//
//  Created by Alexander on 04.06.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Sodi
import shared

final class CreatePostViewModel : BaseViewModel {
    
    private let postsRepository: IPostsRepository = instance()
    
    func start() {
        stop()
        addObserver(postsRepository.addUserPostAsCommonFlow().watch(block: { [self] post in
            self.showProfile()
        }))
    }
    
    private func showProfile() {
        onNavigateState(navTag: NavTag.PROFILE.rawValue)
    }
}
