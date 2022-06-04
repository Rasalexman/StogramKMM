//
//  ProfileViewModel.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import Foundation
import Sodi
import shared

final class ProfileViewModel : BaseViewModel {
    
    private let userRepository: IUserRepository = instance()
    private let postsRepository: IPostsRepository = instance()
    
    
    @Published var selectedUser: IUser? = nil
    @Published var userPosts: [PostEntity] = []
    @Published var profileState: ProfileState = ProfileState(user: nil)
    
    func fetchProfileData(currentUserId: String) {
        addObserver(userRepository.findUserDetailsAsCommonFlow(userId: currentUserId).flatMapCFlow { currentUser in
            return self.postsRepository.findUserPostsAsCommonFlow(user: currentUser).mapCFlow { posts in
                if let currentPosts = posts as? [PostEntity] {
                    return ProfileState(user: currentUser, posts: currentPosts)
                }
                return ProfileState(user: currentUser)
            }
        }.watch { state in
            if let currentState = state as? ProfileState {
                self.selectedUser = currentState.user
                self.userPosts = currentState.posts
            }
            
        })
    }
}
